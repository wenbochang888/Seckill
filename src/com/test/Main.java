package com.test;


import com.dao.cache.RedisDao;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wenbochang
 * @date 2018Äê1ÔÂ12ÈÕ
 */

public class Main {
	public static void main(String[] args) {
		
		JedisPoolConfig config = new JedisPoolConfig();
		String ip = "120.78.159.149";
		int port = 12581;
		String password = "changwenbo";
		//RedisDao redisDao = new RedisDao(config, ip, port, password);
		RedisDao jedisPool = new RedisDao(config, ip, port, password);
		System.out.println(jedisPool.getSeckill(1));
	}
}








