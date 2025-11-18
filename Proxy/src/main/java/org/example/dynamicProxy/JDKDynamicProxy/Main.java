package org.example.dynamicProxy.JDKDynamicProxy;

/**
 * @author liushaoya
 * @since 2025-05-15 11:07
 */
public class Main {
    /**
     * 需求:
     * 外面的人想要大明星唱一首歌
     * 1.获取代理的对象
     *      代理对象 = ProxyUtil.createProxy(大明星的对象);
     * 2.再调用代理的唱歌方法
     *      代理对象.唱歌的方法("只因你太美");
     */
    public static void main(String[] args) {
        BigStar bigStar = new BigStar("鸡哥");
        Star proxy = ProxyUtil.createProxy(bigStar);
        String result = proxy.sing("只因你太美");
        System.out.println(result);
    }
}
