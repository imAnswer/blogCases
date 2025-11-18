package org.example.dynamicProxy.JDKDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 创建一个代理
 * @author liushaoya
 * @since 2025-05-15 10:20
 */
public class ProxyUtil {

    /**
     * 方法的作用： 给一个明星的对象创建一个代理
     * 形参： 被代理的明星对象
     * 返回值： 给明星创建的代理
     * 需求：
     * 1.获取代理的对象
     *      代理对象和 = ProxyUtil.createProxy(大明星的对象)
     * 2.再调用代理的唱歌方法
     *      代理对象.唱歌方法()
     * 3.然后调用下面的invoke方法
     * @param bigStar
     */
    public static Star createProxy(BigStar bigStar) {
        /**
         * java.lang.reflect.Proxy类:提供了为对象产生代理对象的方法:
         * public static object newproxyInstance(classLoader loader, class<?>[] interfaces, InvocationHandler h)
         * 参数一:用于指定用哪个类加载器，去加载生成的代理类
         * 参数二:指定接口，这些接口用于指定生成的代理长什么，也就是有哪些方法
         * 参数三:用来指定生成的代理对象要干什么事情
         */
        Star star = (Star) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{Star.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if("sing".equals(method.getName())) {
                            System.out.println("准备话筒，收钱");
                        } else if ("dance".equals(method.getName())) {
                            System.out.println("准备场地，收钱");
                        }
                        return method.invoke(bigStar, args);
                    }
                });
        return star;
    }
}
