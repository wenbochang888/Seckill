package com.test;

/**
 * @author wenbochang
 * @date 2018年1月12日
 */

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dao.SeckillDao;
import com.dao.cache.RedisDao;
import com.entity.Seckill;

/**
 * @author Chang-pc
 * 
 * 启动时加载IoC容器
 */

@ContextConfiguration({ "classpath:config/spring-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SeckillDaoTest {

	@Autowired
	private SeckillDao seckillDao;

	@Test
	public void testQueryById() throws Exception {

		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}

	/**
	 * 当传递多个参数的时候
	 * mybatis并不能识别那个参数是那个参数
	 * 所以第一种方法可以用map来
	 * 第二种方法可以用@Param("value")里面value指定参数的名字
	 */
	@Test
	public void testQueryAll() throws Exception {

//		Map<String, Object> map = new HashMap<>();
//		map.put("offset", 0);
//		map.put("limit", 2);
//		List<Seckill> seckills = seckillDao.queryAll(map);
		List<Seckill> seckills = seckillDao.queryAll(0, 10);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}

	@Test
	public void testReduceNumber() throws Exception {

		Date killTime = new Date();
		int updateCount = seckillDao.reduceNumber(1000, killTime);
		System.out.println(updateCount);
	}
	
	@Autowired
	RedisDao redisDao;
	
	@Test
	public void testRedis() {
		//get and put
		Seckill seckill = redisDao.getSeckill(1001);
		if (seckill == null) {
			seckill = seckillDao.queryById(1001);
			if (seckill != null) {
				@SuppressWarnings("unused")
				String res = redisDao.putSeckill(seckill);
				System.out.println(res);
				seckill = redisDao.getSeckill(1001);
				System.out.println(seckill);
			}
		}
	}
}










