package com.dliyun.platform.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/4/27 22:32
 */
@Configuration
public class CacheManagerConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues().serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class))
                ).serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
                );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .build();
    }
}
