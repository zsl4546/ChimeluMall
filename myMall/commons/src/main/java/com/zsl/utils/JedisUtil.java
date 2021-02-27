package com.zsl.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/4
 * Time: 20:41
 * Description: Jedis工具类
 */
@Slf4j
public class JedisUtil {
    private static ThreadLocal<Jedis> tl = new ThreadLocal<Jedis>();
    private static Object obj = new Object();
    //连接池对象
    private static JedisPool jedisPool;

    static {
        init();
    }

    /**
     * 获取连接对象的方法，线程安全
     * @return
     * @throws Exception
     */
    public static Jedis getJedis() throws Exception{
        //从当前线程中获取连接对象
        Jedis jedis = tl.get();
        //判断为空的话，创建连接并绑定到当前线程
        if(jedis == null) {
            synchronized (obj) {
                if(tl.get() == null) {
                    jedis = jedisPool.getResource();
                    tl.set(jedis);
                    log.info("get"+Thread.currentThread().getName());
                }
            }
        }
        return jedis;
    }

    /**
     * 获取数据
     */
    public static Object get(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            log.warn("获取数据异常:", e);
        } finally {
            //返还到连接池
            returnJedis();
        }
        return value;
    }

    public static boolean expire(String key, int seconds){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, seconds);
            return true;
        }catch (Exception e){
            log.warn("设置数据生存时间异常",e);
            return false;
        }finally {
            //返还到连接池
            returnJedis();
        }
    }

    //设置数据
    public static boolean set(String key, String value){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key,value);
            return true;
        }catch (Exception e){
            log.warn("设置数据异常",e);
            return false;
        }finally {
            //返还到连接池
            returnJedis();
        }
    }

    //删除数据
    public static void delete(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        }catch (Exception e){
            log.warn("删除数据异常",e);
        }finally {
            //返还到连接池
            returnJedis();
        }
    }

    /**
     * 交还jedis,校验如果jedis失效就将其移出
     */
    public static void returnJedis() {
        Jedis jedis = tl.get();
        if(jedis!=null) {
            tl.remove();
            log.info("remove"+Thread.currentThread().getName());
            jedis.close();
        }
    }

    /**
     * 加载配置文件，并初始化jedisPool
     */
    private static void init() {
        //加载配置文件
        InputStream in = JedisUtil.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            log.error("加载配置文件失败,创建连接池失败");
            e.printStackTrace();
            return;
        }
        //设置配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("redis.pool.maxTotal")));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("redis.pool.maxIdle")));
        jedisPoolConfig.setMinIdle(Integer.parseInt(properties.getProperty("redis.pool.minIdle")));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.pool.maxWaitMillis")));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("redis.pool.testOnBorrow")));
        jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("redis.pool.testOnReturn")));
        //创建jedis连接池
        jedisPool = new JedisPool(jedisPoolConfig,
                properties.getProperty("redis.host"),
                Integer.parseInt(properties.getProperty("redis.port")),
                Integer.parseInt(properties.getProperty("redis.timeout")),
                properties.getProperty("redis.password")
        );
    }
}
