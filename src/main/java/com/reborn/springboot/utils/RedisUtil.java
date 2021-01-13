package com.reborn.springboot.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtil {

    private static Jedis jedis;

    public static Jedis getJedisConnection(){
        if (jedis == null){
            jedis = new Jedis("47.107.45.21",8888);
            jedis.auth("Reborn123,./");
        }
        if (jedis.ping().equalsIgnoreCase("pong")){
            return jedis;
        }else {
            throw new JedisException("连接不到redis");
        }
    }


}
