package org.example.spring.AOPAnnotation.service.impl;

import org.example.spring.AOPAnnotation.Annotation.Facade;
import org.example.spring.AOPAnnotation.request.UserRequest;
import org.example.spring.AOPAnnotation.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-10-04 15:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Facade
    public void print(UserRequest userRequest) {
        System.out.println("姓名 = " + userRequest.getName());
    }
}
