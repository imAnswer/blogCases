package org.example.dynamicProxy.CGLIBDynamicProxy;

/**
 * @author liushaoya
 * @since 2025-05-15 15:24
 */
public class Main {
    public static void main(String[] args) {
        AliSmsService aliSmsService = CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("hello world");
    }
}
