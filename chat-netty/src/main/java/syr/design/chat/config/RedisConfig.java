package syr.design.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import syr.design.chat.utils.RedisUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisConfig {

    @Bean(name = "redisUtils")
    public RedisUtils getRedisUtils(@Value("${spring.redis.host}") String redisHost,
                                     @Value("${spring.redis.port}") Integer redisPort,
                                     @Value("${spring.redis.password}") String password){
        RedisUtils redisUtils = new RedisUtils();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2014);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(1000);
        redisUtils.jedisPool = new JedisPool(config, redisHost, redisPort, 1000, password, 0, "chat-netty");
        return redisUtils;
    }

}
