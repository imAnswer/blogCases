package org.example.dynamicProxy.JDKDynamicProxy;

/**
 * 类似于一个中介，所有需要被代理的方法都需要写到这个接口中
 * @author liushaoya
 * @since 2025-05-15 10:12
 */
public interface Star {

    //可以把所有要被代理的方法定义在接口当中

    //唱歌
    String sing(String name);

    //跳舞
    void dance();
}
