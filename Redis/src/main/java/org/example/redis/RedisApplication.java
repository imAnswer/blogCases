package org.example.redis;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liushaoya
 * @since 2025-10-06 15:40
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "org.example.redis")
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
