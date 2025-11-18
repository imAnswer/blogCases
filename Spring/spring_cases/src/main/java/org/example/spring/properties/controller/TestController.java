package org.example.spring.properties.controller;


import org.example.spring.properties.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2024-12-15 11:36
 */
@RestController
public class TestController {

    @Autowired
    private PropertiesService propertiesService;

    @GetMapping("/test1")
    public void test() {
        propertiesService.print();
    }
}
