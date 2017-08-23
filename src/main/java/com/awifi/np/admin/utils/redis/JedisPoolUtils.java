package com.awifi.np.admin.utils.redis;
 

import org.apache.commons.lang.StringUtils;

import com.awifi.np.admin.utils.PropertiesUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtils {
	private static JedisPool pool;
	private static int dbnum;

	/**
	 * 建立连接池 真实环境，一般把配置参数缺抽取出来。
	 * 
	 */
	private static void createJedisPool() {
		String  maxActiveS = PropertiesUtil.getConfig("redis", "redis.maxActive");
		String maxWaitS = PropertiesUtil.getConfig("redis", "redis.maxWait");
		String maxIdleS = PropertiesUtil.getConfig("redis", "redis.maxIdle");
		String timeBetweenEvictionRunsMillisS = PropertiesUtil.getConfig("redis", "redis.timeBetweenEvictionRunsMillis");
		String testOnBorrowS = PropertiesUtil.getConfig("redis", "redis.testOnBorrow");
		String testWhileIdleS = PropertiesUtil.getConfig("redis", "redis.testWhileIdle");
		String testOnReturnS = PropertiesUtil.getConfig("redis", "redis.testOnReturn");
		Integer maxActive= Integer.valueOf(maxActiveS);
		Integer maxWait= Integer.valueOf(maxWaitS);
		Integer maxIdle= Integer.valueOf(maxIdleS);
		Integer timeBetweenEvictionRunsMillis= Integer.valueOf(timeBetweenEvictionRunsMillisS);
		Boolean testOnBorrow= Boolean.valueOf(testOnBorrowS);
		Boolean testWhileIdle= Boolean.valueOf(testWhileIdleS);
		Boolean testOnReturn= Boolean.valueOf(testOnReturnS);



		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxActive(maxActive);
		// 设置最大阻塞时间，记住是毫秒数milliseconds
		config.setMaxWait(maxWait);
		// 设置空间连接
		config.setMaxIdle(maxIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestWhileIdle(testWhileIdle);
		config.setTestOnReturn(testOnReturn);
		config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 创建连接池
		pool = new JedisPool(config, PropertiesUtil.getConfig("redis", "redis.ip"), Integer.valueOf(PropertiesUtil.getConfig("redis", "redis.port")));
		String dbnumstr = PropertiesUtil.getConfig("redis", "redis.db");
		dbnum = StringUtils.isEmpty(dbnumstr) ? 0 : Integer.valueOf(dbnumstr).intValue();
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (pool == null)
			createJedisPool();
	}

	/**
	 * 获取一个jedis 对象
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		if (pool == null)
			poolInit();
		Jedis jedisInstance = pool.getResource();
		if(dbnum>0) {
			jedisInstance.select(dbnum);
		}
		return jedisInstance;
	}

	/**
	 * 归还一个连接
	 *
	 * @param jedis
	 */
	public static void returnRes(Jedis jedis) {
		pool.returnResource(jedis);
	}

}
