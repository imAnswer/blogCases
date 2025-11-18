package org.example.elasticsearch.documentAnnotation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.UUID;

/**
 * @author liushaoya
 * @since 2025-10-27 11:29
 */
@Configuration
public class CommandLineRunnerConfiguration {
    @Bean
    public CommandLineRunner demo(ProductRepository repo) {
        return args -> {
            // 写入一条
            Product p = new Product();
            p.setId(UUID.randomUUID().toString());
            p.setSku("SKU-10001");
            p.setName("Acme 超轻跑鞋");
            p.setPrice(699.0);
            p.setBrand("ACME");
            p.setCreatedAt(Instant.now());
            repo.save(p);
        };
    }
}
