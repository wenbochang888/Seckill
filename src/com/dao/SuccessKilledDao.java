package com.dao;

import org.apache.ibatis.annotations.Param;

import com.entity.SuccessKilled;
import com.entity.SuccessKilledRecord;

/**
 * @author wenbochang
 * @date 2018年1月12日
 */

public interface SuccessKilledDao {
	
	/**
	 * 秒杀成功，然后插入秒杀成功这条记录
	 * 可以过滤重复，一个人只能秒杀一次
	 * 返回1就是插入成功，返回0就是插入失败
	 */
	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	/**
	 * 根据id查询SuccessKilled, 并携带秒杀产品对象实体
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	
	SuccessKilledRecord queryAllById(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}








