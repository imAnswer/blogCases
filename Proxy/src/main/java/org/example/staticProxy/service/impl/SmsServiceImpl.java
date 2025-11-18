package org.example.staticProxy.service.impl;

import org.example.staticProxy.service.SmsService;

/**
 * 被代理类
 * @author liushaoya
 * @since 2025-07-14 20:47
 */
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
