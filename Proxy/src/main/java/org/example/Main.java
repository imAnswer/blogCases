package org.example;

import org.example.staticProxy.service.SmsService;
import org.example.staticProxy.service.impl.SmsProxy;
import org.example.staticProxy.service.impl.SmsServiceImpl;

/**
 * @author liushaoya
 * @since 2025-05-15 10:02
 */
public class Main {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("java");
    }
}
