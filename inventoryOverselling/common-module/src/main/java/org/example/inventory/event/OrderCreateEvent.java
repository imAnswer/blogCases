package org.example.inventory.event;

import org.example.inventory.entity.TradeOrder;
import org.springframework.context.ApplicationEvent;

/**
 * @author liushaoya
 * @since 2025-11-14 19:44
 */
public class OrderCreateEvent extends ApplicationEvent {

    public OrderCreateEvent(TradeOrder tradeOrder) {
        super(tradeOrder);
    }
}
