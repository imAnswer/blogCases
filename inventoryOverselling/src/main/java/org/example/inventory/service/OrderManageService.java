package org.example.inventory.service;

import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.OrderResponse;

/**
 * @author liushaoya
 * @since 2025-11-14 17:18
 */
public interface OrderManageService {
    /**
     * 订单创建并异步执行确认
     *
     * @param request
     * @return
     */
    OrderResponse createAndAsyncConfirm(OrderCreateRequest request);

    /**
     * 订单确认
     *
     * @param request
     * @return
     */
    OrderResponse confirm(OrderConfirmRequest request);
}
