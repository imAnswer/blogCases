package org.example.redis.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-10-05 22:26
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient(){
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.64.132:6379");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

}
