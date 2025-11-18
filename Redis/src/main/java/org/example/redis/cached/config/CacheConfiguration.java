package org.example.redis.cached.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-10-08 20:41
 */
@Configuration
@EnableMethodCache(basePackages = "org.example.redis")
public class CacheConfiguration {
}
