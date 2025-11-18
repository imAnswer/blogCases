package org.example.spring.BeanLifeCycle;

/**
 * @author liushaoya
 * @since 2025-07-27 16:54
 */
public class UserService3 {
    public void init3() throws Exception {
        System.out.println("初始化3");
    }

    public void destroy3() throws Exception {
        System.out.println("销毁3");
    }
}
