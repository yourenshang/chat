package syr.design.chat.utils;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtils {

    public JedisPool jedisPool;

    private void release(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public <T> T hget(String key, String fieid, Class<T> tc) {
        return null;
    }

    public <T> Long hset(String key, String fieid, T obj) {
        return null;
    }

    public <T> T get(String key, Class<T> ts){
        Jedis jedis;
        jedis = jedisPool.getResource();
        String hget = jedis.get(key);
        release(jedis);
        return JSON.parseObject(hget, ts);
    }

    public boolean hasKey(String key){
        Jedis jedis;
        jedis = jedisPool.getResource();
        return jedis.exists(key);
    }

}
