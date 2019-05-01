package syr.design.chat.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

    private StringRedisTemplate stringRedisTemplate;

    private RedisTemplate<String, Object> objectRedisTemplate;

    public RedisUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> objectRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectRedisTemplate = objectRedisTemplate;
    }

    /**
     * 指定缓存失效时间
     */
    public boolean setStringExpireTime(String key, long time) {
        if (time > 0) {
            stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     */
    public Long getStringExpireTime(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKeyString(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     */
    public Boolean deleteString(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 普通缓存获取
     */
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     */
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     */
    public void setString(String key, String value, long time) {
        if (time > 0) {
            stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 值递增
     */
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 值递减
     */
    public Long decrement(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 对象缓存获取
     */
    public Object getObject(String key) {
        return objectRedisTemplate.opsForValue().get(key);
    }

    /**
     * 对象缓存放入
     */
    public void setObject(String key, Object value) {
        objectRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     */
    public void setObject(String key, Object value, long time) {
        if (time > 0) {
            objectRedisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 指定缓存失效时间
     */
    public boolean setObjectExpireTime(String key, long time) {
        if (time > 0) {
            objectRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 根据key 获取过期时间
     */
    public Long getObjectExpireTime(String key) {
        return objectRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKeyObject(String key) {
        return objectRedisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     */
    public Boolean deleteObject(String key) {
        return objectRedisTemplate.delete(key);
    }

}
