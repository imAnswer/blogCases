package org.example.validator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * 用户校验器
 * @author liushaoya
 * @since 2025-11-04 19:38
 */
@Component
@Primary
public class UserValidator extends BaseOrderCreateValidator {

    @Override
    public void doValidate(){
        System.out.println("第一个责任链开始");
        System.out.println("第一个校验责任链执行");
        System.out.println("第一个责任链结束");
    }


}
