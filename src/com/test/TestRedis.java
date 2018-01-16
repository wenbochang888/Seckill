package com.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author Chang-pc
 * redis ¹Ù·½ÎÄµµ http://xetorthio.github.io/jedis/
 */

public class TestRedis {
	public static void main(String[] args) {
		
		connectRedis();
		
		connectionRedisPool();
	}

	private static void connectionRedisPool() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(10);
		JedisPool jedisPool = 
				new JedisPool(config, "120.78.159.xxx", 100, 1000, "xxx");
		Jedis jedis = null;
		
		try {
			jedis = jedisPool.getResource();
			System.out.println(jedis.get("name"));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
	}

	private static void connectRedis() {
		
		Jedis jedis = new Jedis("120.78.159.xxx", 100);
		jedis.auth("xxxx");
		System.out.println(jedis.get("name"));
		jedis.close();
	}
}









