package org.example.spring.manualGetBean.service.impl;

import org.example.spring.manualGetBean.service.ChildrenService;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2024-12-10 22:47
 */
@Service
public class ChildrenServiceImpl implements ChildrenService {
    @Override
    public void childrenTest() {
        System.out.println("This is children");
    }
}
