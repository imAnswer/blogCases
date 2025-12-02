package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.TradeOrderEvent;

@Getter
@Setter
public class OrderTimeoutRequest extends BaseOrderUpdateRequest {

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.TIME_OUT;
    }
}
