package com.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.entity.Seckill;

/**
 * @author wenbochang
 * @date 2018年1月12日
 */

public interface SeckillDao {

	/**
	 * 这是一个减库存的操作
	 * 
	 * 比如张三秒杀成功了，那么应该将库存中的number数量减少一个
	 * 并且插入秒杀成功的记录，SuccessKilled
	 * 
	 * 这是一个事务，如果插入成功，则返回 > 1 否则 返回 0
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 用商品的Id来查询商品明细
	 */
	Seckill queryById(long seckillId);

	/**
	 * 查询所有记录。
	 * 根据偏移量查询秒杀商品的列表
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	//List<Seckill> queryAll(Map<String, Object> map);
	
	/**
	 * 使用存储过程执行秒杀
	 * 
	 * @param paramMap
	 */
	void killByProcedure(Map<String, Object> paramMap);
}






















