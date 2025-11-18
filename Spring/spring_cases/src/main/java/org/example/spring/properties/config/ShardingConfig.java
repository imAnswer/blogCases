package org.example.spring.properties.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author liushaoya
 * @since 2024-12-15 11:15
 */
@Configuration
@PropertySource("classpath:sharding.properties")
public class ShardingConfig {
}
