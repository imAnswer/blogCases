package org.example.spring.BeanLifeCycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 注解方式实现初始化和销毁
 * @author liushaoya
 * @since 2025-07-27 16:24
 */
public class UserService1 {
    @PostConstruct
    public void init() {
        System.out.println("初始化");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁");
    }
}
