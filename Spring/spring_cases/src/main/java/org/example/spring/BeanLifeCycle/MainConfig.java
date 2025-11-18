package org.example.spring.BeanLifeCycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-07-27 16:25
 */
@Configuration
public class MainConfig {
    @Bean
    public UserService1 userService1() {
        return new UserService1();
    }

    @Bean
    public UserService2 userService2() {
        return new UserService2();
    }

    @Bean(initMethod = "init3", destroyMethod = "destroy3")
    public UserService3 userService3() {
        return new UserService3();
    }
}
