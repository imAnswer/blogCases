package org.example.spring;

import org.springframework.context.annotation.Bean;

/**
 * @author liushaoya
 * @since 2025-07-10 21:00
 */
public class MsgConfig {

    @Bean
    public String hello() {
        return "hello world";
    }
}
