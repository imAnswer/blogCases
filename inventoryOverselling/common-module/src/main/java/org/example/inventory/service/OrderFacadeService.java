package org.example.inventory.service;

import org.example.inventory.request.OrderCancelRequest;
import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.vo.TradeOrderVO;

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
    OrderResponse confirm(OrderConfirmRequest request);

    /**
     * 取消订单
     *
     * @param request
     * @return
     */
    OrderResponse cancel(OrderCancelRequest request);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    SingleResponse<TradeOrderVO> getTradeOrder(String orderId);
}
