package com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.dao.SeckillDao;
import com.dao.SuccessKilledDao;
import com.dao.cache.RedisDao;
import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.entity.Seckill;
import com.entity.SuccessKilled;
import com.enums.SeckillStatEnum;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.exception.SeckillException;

/**
 * 业务逻辑层的具体实现
 * 
 * @author wenbochang
 * @date 2018年1月13日
 */

@Service
public class SeckillServiceImpl implements SeckillService {

	//注入service依赖
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;

	// 用于混淆md5
	private final String salt = "cwb1^^^2^^^3zyj";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/**
	 * 当秒杀时间没有到的时候，输出系统时间和秒杀时间 否则输出秒杀的接口，就是让用户可以点击秒杀的按钮
	 * 
	 * 判断了三种类型，第一种是该id商品不存在的情况，直接返回false 第二种是不在时间范围内，没有到达开始时间，或者已经结束了。
	 * 第三种则为正常，那么验证md5，防止数据被别人篡改和拼接。
	 * 
	 * @param seckillId
	 */
	@Override
	public Exposer exportSeckillUrl(long seckillId) {

		/**
		 * 2018年1月15日14:59:47
		 * 缓存优化1 ：将商品放到redis缓存中去
		 *  
		 * get from cache
		 * if null
		 * 	  get db
		 *	  put cache
		 */
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			System.out.println("111");
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			} else {
				redisDao.putSeckill(seckill);
			}
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}

		// 转化特定字符串的编码, 不可逆
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	// 生成md5算法，用了一个spring的工具类 DigestUtils.md5DigestAsHex(里面需要bytes数组)
	private String getMd5(long seckillId) {
		String base = salt + "/" + seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	/**
	 * 注意：  这是重点 ！！！！！！！！！！！！
	 * spring的事务只有在运行期出现异常才会进行回滚
	 * 如果用try catch进行了捕获，spring认为这不是异常也就不会进行回滚了
	 * 
	 * 所以必须在catch里面 明显的throw new SeckillException("seckill data rewrite");
	 * 异常，spring才会进行回滚
	 * 
	 * 使用注解配置事务方法的优点：
	 * 1：开发团队明确标注事务方法编程的方法。所有人一看就知道这是一个事务
	 * 2：保证事务方法执行时间尽可能的短
	 * 3：不是所有的方法都需要事务
	 */

	/**
	 * 执行秒杀 md5是防止用户 拼接url地址
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	@Transactional
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {

		// 验证md5
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		/**
		 * 执行秒杀逻辑 
		 * 1. 首先减少库存 
		 * 2. 记录购买的行为
		 */
		try {
			Date nowTime = new Date();
			/**
			 * 这里为什么要用sql语句来判断库存充足或者不充足呢？
			 * 假如一个人先查询商品的数量，发觉 > 1 然后就去减少
			 * 但另外一个人已经减少商品为0 
			 * 但sql已经执行成功了，又将数量为0的商品卖了一次
			 * 这就出现了数据错误的现象
			 */
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				// 没有秒杀成功(库存不足，秒杀的太慢)，没有更新记录（即减少库存）
				throw new SeckillCloseException("goods is lack, seckill is closed");
			} else {
				// 秒杀成功，并记录购买的行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount <= 0) {
					// 重复秒杀，数据库中记录不唯一
					throw new RepeatKillException("seckill repeated");
				} else {
					// 秒杀成功
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2){
			throw e2;
		} catch (Exception e) {
			//所有异常全部捕获,编译异常转化为运行期异常
			//spring会帮我们rollback
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	@Override
	public SeckillExecution executeSeckillProduce(long seckillId, long userPhone, String md5) {
		
		if (md5 == null || !md5.equals(getMd5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);
		// 执行存储过程，result被赋值
		try {
			seckillDao.killByProcedure(map);
			// 获取result
			int result = MapUtils.getInteger(map, "result", -2);
			if (result == 1) {
				SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
		} catch (Exception e) {
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}
























