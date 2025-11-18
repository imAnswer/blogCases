package org.example.spring.BeanLifeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 实现InitializingBean, DisposableBean接口的方式完成初始化和销毁
 * @author liushaoya
 * @since 2025-07-27 16:41
 */
public class UserService2 implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化2");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁2");
    }
}
