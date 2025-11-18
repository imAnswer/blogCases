package org.example.config;

import org.example.validator.GoodsBookValidator;
import org.example.validator.GoodsValidator;
import org.example.validator.OrderCreateValidator;
import org.example.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushaoya
 * @since 2025-11-04 19:37
 */
@Configuration
public class OrderCreateValidatorConfig {
    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GoodsBookValidator goodsBookValidator;

    @Bean
    public OrderCreateValidator orderValidatorChain() {
        userValidator.setNext(goodsValidator);
        goodsValidator.setNext(goodsBookValidator);
        return userValidator;
    }
}
