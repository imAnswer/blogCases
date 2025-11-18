package org.example.inventory.service;

import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.OrderResponse;

/**
 * @author liushaoya
 * @since 2025-11-14 16:46
 */
public interface OrderFacadeService {
    /**
     * 创建订单
     *
     * @param request
     * @return
     */
    OrderResponse create(OrderCreateRequest request);

    /**
     * 订单确认
     *
     * @param request
     * @return
     */
    public OrderResponse confirm(OrderConfirmRequest request);
}
