package com.service;

import java.util.List;

import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.entity.Seckill;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.exception.SeckillException;

/**
 * 业务逻辑层接口
 * 
 * @author wenbochang
 * @date 2018年1月12日
 */

public interface SeckillService {
	
	/**
	 * 查询所有秒杀商品的记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 当秒杀时间没有到的时候，输出系统时间和秒杀时间
	 * 否则输出秒杀的接口，就是让用户可以点击秒杀的按钮
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀
	 * md5是防止用户 拼接url地址
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
		throws SeckillException, RepeatKillException, SeckillCloseException;
	
	/**
	 * 存储过程
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * @throws SeckillException
	 * @throws RepeatKillException
	 * @throws SeckillCloseException
	 */
	SeckillExecution executeSeckillProduce(
			long seckillId, long userPhone, String md5);
}














