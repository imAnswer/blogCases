package org.example.redis.redisson.lock.config;

import org.example.redis.redisson.lock.DistributeLockAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-10-06 18:20
 */
@Configuration
public class DistributeLockConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DistributeLockAspect distributeLockAspect(){
        return new DistributeLockAspect();
    }
}
