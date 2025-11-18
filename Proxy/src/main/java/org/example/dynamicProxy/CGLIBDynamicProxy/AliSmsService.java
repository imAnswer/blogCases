package org.example.dynamicProxy.CGLIBDynamicProxy;

/**
 * 被代理类（目标类）
 * 实现一个使用阿里云发送短信的类
 * @author liushaoya
 * @since 2025-05-15 15:13
 */
public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
