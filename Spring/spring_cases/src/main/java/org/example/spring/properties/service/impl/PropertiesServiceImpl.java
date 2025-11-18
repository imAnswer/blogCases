package org.example.spring.properties.service.impl;

import org.example.spring.properties.service.PropertiesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2024-12-15 11:35
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Value("${month}")
    private String month;

    @Override
    public void print() {
        System.out.println(month);
    }
}
