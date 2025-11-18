package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.UserType;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liushaoya
 * @since 2025-11-14 19:48
 */
@Setter
@Getter
public abstract class BaseOrderUpdateRequest extends BaseOrderRequest {

    /**
     * 订单id
     */
    @NotNull(message = "orderId 不能为空")
    private String orderId;

    /**
     * 操作时间
     */
    @NotNull(message = "operateTime 不能为空")
    private Date operateTime;

    /**
     * 操作人
     */
    @NotNull(message = "operator 不能为空")
    private String operator;

    /**
     * 操作人类型
     */
    @NotNull(message = "operatorType 不能为空")
    private UserType operatorType;
}
