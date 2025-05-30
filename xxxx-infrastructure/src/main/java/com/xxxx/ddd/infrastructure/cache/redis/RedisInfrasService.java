package com.xxxx.ddd.infrastructure.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisInfrasService {
    void setString(String key, String value);
    String getString(String key);

    void setObject(String key, Object value);
    <T> T getObject(String key, Class<T> targetClass);

    // void put(String key, Object value, long timeout, TimeUnit unit);
    // void put(String key, Object value, long expireTime);

    // delete redis by key
    void delete(String key);
    RedisTemplate<String, Object> getRedisTemplate();
    // init
    void setInt(String key, int value);
    int getInt(String key);
}
