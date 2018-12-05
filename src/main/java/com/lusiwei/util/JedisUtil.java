package com.lusiwei.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: lusiwei
 * @Date: 2018/12/4 19:42
 * @Description:
 */
public class JedisUtil {
    private final static String IP;
    private final static Integer PORT;
    private final static Integer MAX;
    private final static Integer MAXIDLE;
    private final static Integer MINIDLE;
    private final static JedisPool JEDIS_POOL;

    static {
        IP = PropertiesUtil.getProperty("redis.ip", "192.168.1.16");
        PORT = Integer.valueOf(PropertiesUtil.getProperty("redis.port", "6379"));
        MAX = Integer.valueOf(PropertiesUtil.getProperty("redis.max", "20"));
        MAXIDLE = Integer.valueOf(PropertiesUtil.getProperty("redis.maxIdle", "8"));
        MINIDLE = Integer.valueOf(PropertiesUtil.getProperty("redis.minIdle", "3"));
        JEDIS_POOL = getJedisPool();
    }

    private static JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(MAXIDLE);
        jedisPoolConfig.setMaxIdle(MINIDLE);
        jedisPoolConfig.setMaxTotal(MAX);
        return new JedisPool(jedisPoolConfig, IP, PORT, 6000);
    }

    public static Jedis getJedis() {
        Jedis resource = JEDIS_POOL.getResource();
        resource.auth(PropertiesUtil.getProperty("redis.password"));
        return resource;
    }


    public static void main(String[] args) {
        Jedis jedis = JedisUtil.getJedis();
        System.out.println(jedis);
        jedis.auth(PropertiesUtil.getProperty("redis.password"));
        jedis.set("java", "IDEA java test");
        System.out.println(jedis.get("java"));
        jedis.close();
        System.out.println(jedis);
    }
}
