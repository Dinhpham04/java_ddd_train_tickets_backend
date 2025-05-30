package com.xxxx.ddd.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory); // thiết lập cấu hình từ application.properties
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        // serializer: chuyển object thành json và ngược lại, Object.class (cho bất kỳ kiểu dữ liệu nào)

        // sử dụng StringRedisSerializer để tuần tự hóa và giải tuần tự hóa các giá tr khi redis
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // chuyển key thành dạng String để đễ ọc
        redisTemplate.setValueSerializer(serializer);

        // Khóa hash cũng sử dụng phương thức tuần tự hóa StringRedisSerializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.afterPropertiesSet(); // đánh dấu hoàn tất cấu hình
        return redisTemplate;
    }
}


