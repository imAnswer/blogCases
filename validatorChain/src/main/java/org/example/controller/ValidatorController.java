package org.example.controller;

import org.example.validator.OrderCreateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-11-04 19:50
 */
@RestController
public class ValidatorController {

    @Autowired
    private OrderCreateValidator orderCreateValidator;

    @GetMapping("/validator")
    public String validator() {
        orderCreateValidator.validate();
        return "hello world";
    }
}
