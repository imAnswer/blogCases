package org.example.validator;

import org.springframework.stereotype.Component;

/**
 * 商品校验器
 * @author liushaoya
 * @since 2025-11-04 19:38
 */
@Component
public class GoodsValidator extends BaseOrderCreateValidator {


    @Override
    protected void doValidate() {
        System.out.println("第二个责任链开始");
        System.out.println("第二个校验责任链执行");
        System.out.println("第二个责任链结束");
    }


}
