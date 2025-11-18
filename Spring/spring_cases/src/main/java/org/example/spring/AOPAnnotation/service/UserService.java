package org.example.spring.AOPAnnotation.service;


import org.example.spring.AOPAnnotation.request.UserRequest;

/**
 * @author liushaoya
 * @since 2025-10-04 15:35
 */
public interface UserService {
    void print(UserRequest userRequest);
}
