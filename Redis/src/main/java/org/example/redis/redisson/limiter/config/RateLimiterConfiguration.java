package org.example.redis.redisson.limiter.config;

import org.example.redis.redisson.limiter.SlidingWindowRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-10-05 22:22
 */
@Configuration
public class RateLimiterConfiguration {
    @Bean
    public SlidingWindowRateLimiter slidingWindowRateLimiter() {
        return new SlidingWindowRateLimiter();
    }
}
