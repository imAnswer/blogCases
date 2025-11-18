package org.example.spring.manualGetBean.service.impl;

import org.example.spring.manualGetBean.service.AdultService;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2024-12-10 22:47
 */
@Service
public class Adult01ServiceImpl implements AdultService {
    @Override
    public void adultTest() {
        System.out.println("This is adult 01");
    }
}
