package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.TradeOrderEvent;

@Getter
@Setter
public class OrderCancelRequest extends BaseOrderUpdateRequest {

    @Override
    public TradeOrderEvent getOrderEvent() {
        return TradeOrderEvent.CANCEL;
    }
}
