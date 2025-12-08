package org.example.inventory.service;

import org.example.inventory.entity.TradeOrder;

public interface OrderReadService {
    TradeOrder getOrder(String orderId);

}
