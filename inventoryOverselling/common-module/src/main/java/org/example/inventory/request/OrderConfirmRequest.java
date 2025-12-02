package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.constant.TradeOrderEvent;

/**
 * @author liushaoya
 * @since 2025-11-14 19:48
 */
@Getter
@Setter
public class OrderConfirmRequest extends BaseOrderUpdateRequest {

    /**
     * 买家Id
     */
    private String buyerId;

    /**
     * 商品Id
     */
    private String goodsId;

    /**
     * 商品类型
     */
    private GoodsType goodsType;

    /**
     * 数量
     */
    private Integer itemCount;

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.CONFIRM;
    }
}
