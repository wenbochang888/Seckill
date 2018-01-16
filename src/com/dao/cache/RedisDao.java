package com.dao.cache;

import com.entity.Seckill;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 这个序列化用了第三方的jar包
 * protostuff
 * 相比如java自身的序列化，他序列的时间更加短
 * 字节更加少
 * 
 * @author wenbochang
 * @date 2018年1月15日
 */

public class RedisDao {
	
	public RedisDao() {
		// TODO Auto-generated constructor stub
	}

	private JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = 
			RuntimeSchema.createFrom(Seckill.class);

	
	public RedisDao(JedisPoolConfig config, String ip, int port, String password) {
		jedisPool = new JedisPool(config, ip, port, 1000, password);
	}

	public Seckill getSeckill(long seckillId) {
		/**
		 * 获取redis缓存
		 * 拿到的是一串二进制数组，然后进行反序列化
		 * 采用自定义的序列化
		 * protostuff
		 */
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					// seckill被反序列化
					return seckill;
				}
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} 
		
		return null;
	}

	public String putSeckill(Seckill seckill) {
		// set Object(Seckill) -> 序列号 -> byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				// 超时缓存
				int timeout = 60 * 60; //3600s 即一个小时
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}









