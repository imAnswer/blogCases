package org.example.dynamicProxy.CGLIBDynamicProxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * 创建代理类的工具类
 * @author liushaoya
 * @since 2025-05-15 15:20
 */
public class CglibProxyFactory {
    public static <T> T getProxy(Class<T> clazz) {
        // 创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(clazz);
        // 设置方法拦截器
        enhancer.setCallback(new DebugMethodInterceptor());
        // 创建代理类
        return (T) enhancer.create();
    }
}
