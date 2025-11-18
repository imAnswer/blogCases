package org.example.spring.BeanLifeCycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liushaoya
 * @since 2025-07-27 16:24
 */
public class Run {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        UserService2 bean = annotationConfigApplicationContext.getBean(UserService2.class);
        annotationConfigApplicationContext.close();
    }
}
