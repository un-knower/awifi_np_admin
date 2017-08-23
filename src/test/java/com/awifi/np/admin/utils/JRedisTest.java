package com.awifi.np.admin.utils;

import org.junit.Test;

import com.awifi.np.admin.utils.redis.JedisUtil;

public class JRedisTest {

	@Test
	public void test(){
		
		try{
		JedisUtil.createJedis("localhot", 80, "dongdondg");
		}catch(Exception e){
			
		}
		JedisUtil.hset("aa", "aa","dd");
		JedisUtil.hget("aa", "aa");
		JedisUtil.hdel("aa","aa");
		JedisUtil.hgetAll("aa");
		JedisUtil.keys("aa");
	}
}
