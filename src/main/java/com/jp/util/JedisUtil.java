package com.jp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 *
 * 获取jedis对象
 * @author panhj
 * @ClassName JedisUtil
 * @Description TODO
 * @Version 1.0
 *
 */
public class JedisUtil {

	/**
	 * redis连接池
	 */
	private static JedisPool pool = null;

	/**
	 * log4j日志对象
	 */
	private static Logger log;

	static {
		log = LogManager.getLogger(JedisUtil.class);
	}

	/**
	 * 配置文件 config.properties
	 */
	private static Properties properties = null;

	/**
	 * 静态方法访问时，直接访问不需要实例化
	 * @return
	 */
	public static Properties getProperties() {//synchronized表示同时只能一个线程进行实例化
		if (properties == null) {//如果两个进程同时进入时，同时创建很多实例，不符合单例
			try {
				synchronized (JedisUtil.class) {
					if (properties == null) {// Double Check, 双重检查
						properties = readProperties("conf/redis.properties");
						log.info("redis.properties 初始化完毕:" + properties);
					}
				}
			} catch (Exception e) {
				log.error("初始化加载配置文件异常：", e);
			}
		}
		return properties;
	}

	public static Properties readProperties(String fileName) throws Exception {
		String path = JedisUtil.class.getClassLoader().getResource(fileName).getPath();
		Properties properties = new Properties();
		properties.load(new InputStreamReader(new FileInputStream(path), "utf-8"));
		return properties;
	}

	/**
	 * redis连接池获取连接
	 * @return
	 */
	public static Jedis getInstance() {
		if (pool == null) {
			// 建立连接池配置参数
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置最大连接数
			config.setMaxTotal(Integer.parseInt(getProperties().getProperty("redis.maxActive")));
			// 设置最大阻塞时间，记住是毫秒数milliseconds
			config.setMaxWaitMillis(Integer.parseInt(getProperties().getProperty("redis.maxWait")));
			// 设置空间连接
			config.setMaxIdle(Integer.parseInt(getProperties().getProperty("redis.maxIdle")));
			// 设置空间连接
			config.setMinIdle(Integer.parseInt(getProperties().getProperty("redis.minIdle")));

			String ip = getProperties().getProperty("redis.ip");
			Integer port = Integer.parseInt(getProperties().getProperty("redis.port"));

			pool = new JedisPool(ip, port);
		}
		String redisAuth = getProperties().getProperty("redis.password");
		Jedis jedis = pool.getResource();
		//账号验证
		if (StringTools.notEmpty(redisAuth)) {
			jedis.auth(redisAuth);
		}
		jedis.select(3);
		return jedis;
	}

	/**
	 * 关闭REDIS连接
	 * @param jedis
	 */
	public static void closeJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	* 查询redis获取String
	*
	* @param key redis key
	* @return String
	*/
	public static String queryString(String key) {
		Jedis jedis = null;
		String str = null;
		try {
			jedis = JedisUtil.getInstance();
			str = jedis.get(key);
			jedis.del(key);
		} catch (Exception e) {
			log.error("queryJson异常：", e);
		} finally {
			JedisUtil.closeJedis(jedis);
		}
		return str;
	}

	/**
	 * 存储String数据到redis,此方法只做验证临时token
	 *
	 * @param key redis key
	 * @return String
	 */
	public static void saveString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getInstance();
			//		Transaction multi = jedis.multi();//开启事务
			jedis.set(key, value);
			//		multi.exec();//提交事务
		} catch (Exception e) {
			log.error("queryJson异常：", e);
		} finally {
			JedisUtil.closeJedis(jedis);
		}
	}

	/**
	 * 查询redis获取JSONObject
	 *
	 * @param key redis key
	 * @return JSONObject
	 */
	public static JSONObject queryJson(String key) {
		JSONObject jsonObject = null;
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getInstance();
			String str = jedis.get(key);
			jsonObject = JSONObject.parseObject(str);

		} catch (Exception e) {
			log.error("queryJson异常：", e);
		} finally {
			JedisUtil.closeJedis(jedis);
		}
		return jsonObject;
	}

	/**
	 * 查询redis获取JSONArray
	 *
	 * @param key redis key
	 * @return JSONArray
	 */
	public static JSONArray queryArray(String key) {
		JSONArray jsonArray = null;
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getInstance();
			String str = jedis.get(key);
			jsonArray = JSONArray.parseArray(str);

		} catch (Exception e) {
			log.error("queryArray异常：", e);
		} finally {
			JedisUtil.closeJedis(jedis);
		}
		return jsonArray;
	}

	/**
	 * 查询redis获取List
	 *
	 * @param key redis key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<JSONObject> queryList(String key, Integer start, Integer end) {
		List<JSONObject> listMap = new ArrayList<>();
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getInstance();
			List<String> list = jedis.lrange(key, start, end);
			for (String value : list) {
				listMap.add(JSONObject.parseObject(value));
			}

		} catch (Exception e) {
			log.error("queryList 异常：", e);
		} finally {
			JedisUtil.closeJedis(jedis);
		}
		return listMap;
	}

	/**
	 * 存储数据到redis
	 * @param result
	 */
	public static void saveToRedis(Map<String, String> result) {
		long startTime = System.currentTimeMillis();
		Jedis jedis = null;
		Pipeline p = null;
		try {
			jedis = JedisUtil.getInstance();
			p = jedis.pipelined();
			p.multi();//开启事务
			for (String key : result.keySet()) {
				p.set(key, result.get(key));
			}
			p.exec();//提交事务
			p.sync();
		} catch (Exception e) {
			if (p != null) {
				p.discard();//回滚事务（取消事务）
			}
			log.error("信息存储redis异常：", e);
		} finally {
			if (p != null) {
				try {
					p.close();
				} catch (IOException e) {
					log.error("信息存储数据到redis管道关闭异常：", e);
				}
			}
			JedisUtil.closeJedis(jedis);
		}
		long endTime = System.currentTimeMillis();
		log.info("信息redis管道插入耗时" + (endTime - startTime) + ":ms，插入条数：" + result.size());
	}

	/**
	 * 存储数据到redis
	 * @param result
	 */
	public static void saveList(Map<String, List<String>> result) {
		long startTime = System.currentTimeMillis();
		Jedis jedis = null;
		Pipeline p = null;
		try {
			jedis = JedisUtil.getInstance();
			p = jedis.pipelined();
			p.multi();//开启事务
			for (String key : result.keySet()) {
				p.del(key);
				List<String> array = result.get(key);
				String[] res = array.toArray(new String[array.size()]);
				p.rpush(key, res);
			}
			p.exec();//提交事务
			p.sync();
		} catch (Exception e) {
			if (p != null) {
				p.discard();//回滚事务（取消事务）
			}
			log.error("信息存储redis异常：", e);
		} finally {
			if (p != null) {
				try {
					p.close();
				} catch (IOException e) {
					log.error("信息存储数据到redis管道关闭异常：", e);
				}
			}
			JedisUtil.closeJedis(jedis);
		}
		long endTime = System.currentTimeMillis();
		log.info("信息redis管道插入耗时" + (endTime - startTime) + ":ms，插入条数：" + result.size());
	}
}
