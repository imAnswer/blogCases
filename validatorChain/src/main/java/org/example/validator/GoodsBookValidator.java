package org.example.validator;

import org.springframework.stereotype.Component;

/**
 * 商品预约校验器
 * @author liushaoya
 * @since 2025-11-04 19:39
 */
@Component
public class GoodsBookValidator extends BaseOrderCreateValidator {


    @Override
    protected void doValidate() {
        System.out.println("第三个责任链开始");
        System.out.println("第三个校验责任链执行");
        System.out.println("第三个责任链结束");
    }

}