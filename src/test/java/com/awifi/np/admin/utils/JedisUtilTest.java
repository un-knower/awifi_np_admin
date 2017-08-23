package com.awifi.np.admin.utils;

import org.junit.Test;

import com.awifi.np.admin.utils.redis.JedisPoolUtils;
import com.awifi.np.admin.utils.redis.JedisUtil;

public class JedisUtilTest {

	
	
	@Test
	public void test(){
		
		JedisUtil.createJedis();
		JedisUtil.set("test", new byte[]{});
		JedisUtil.set("test", "hello", 10);
		JedisUtil.set("test", "hello");
		JedisUtil.get("test");
		JedisUtil.get(null);
		JedisUtil.set("test", "hello");
		JedisUtil.getValue("test");
		JedisUtil.getValue(null);
		
		JedisUtil.setex("test", 10, "hello");
		JedisUtil.del("test");
		
		JedisUtil.hset("test", "mm", "hello");
		JedisUtil.hget("test", "name");
		JedisUtil.hset("test", null, "hello");
		JedisUtil.hset("test", "mm", "hello");
		JedisUtil.hdel("test", "mm");
		JedisUtil.hdel("test", null);
		
		JedisUtil.hset("test", "mm", "hello");
		JedisUtil.hgetAll("test");
		JedisUtil.hgetAll(null);
		
		JedisUtil.keys("test");
		JedisUtil.keys(null);
		JedisUtil.set(null, new byte[]{});
		JedisUtil.set(null, "hello");
		JedisUtil.set(null, "hello", 10);
		JedisUtil.set("test", "hello");
//		JedisUtil.del(null);
		JedisUtil.del("test");
		JedisUtil.set("test", "hello");
		JedisUtil.deleteBulk("test");
		JedisUtil.deleteBulk(null);
		JedisUtil.setex(null, 10, "hello");
		JedisUtil.setex("test", 10, "hello");
		JedisUtil.setex("test", 10, null);
	}
	
	@Test
	public void test2(){
		
		JedisUtil.createJedis("localhost", 8080);
		
	}
}
