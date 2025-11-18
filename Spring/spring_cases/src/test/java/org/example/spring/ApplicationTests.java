package org.example.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(properties = {"AAA.BBB = 12345"})
@Import({MsgConfig.class})
class ApplicationTests {

    @Autowired
    private String hello;

    @Value("${AAA.BBB}")
    private String msg;

    @Test
    void testHello() {
        System.out.println(hello);
    }

    @Test
    void contextLoads() {
        System.out.println(msg);
    }



}
