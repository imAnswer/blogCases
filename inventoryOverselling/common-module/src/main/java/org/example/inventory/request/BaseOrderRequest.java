package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.TradeOrderEvent;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-10-29 10:26
 */
@Getter
@Setter
public abstract class BaseOrderRequest extends BaseRequest {

    /**
     * 操作幂等号
     */
    @NotNull(message = "identifier 不能为空")
    private String identifier;

    /**
     * 获取订单事件
     *
     * @return
     */
    public abstract TradeOrderEvent getOrderEvent();
}
