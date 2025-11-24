package org.example.inventory.service.impl;

import org.example.inventory.constant.OrderErrorCode;
import org.example.inventory.exception.OrderException;
import org.example.inventory.request.GoodsSaleRequest;
import org.example.inventory.request.InventoryRequest;
import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.BaseResponse;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.GoodsFacadeService;
import org.example.inventory.service.InventoryFacadeService;
import org.example.inventory.service.OrderFacadeService;
import org.example.inventory.service.OrderManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-11-14 16:46
 */
@Service
public class OrderFacadeServiceImpl implements OrderFacadeService {
    @Autowired
    private InventoryFacadeService inventoryFacadeService;
    @Autowired
    private OrderManageService orderService;
    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Override
    public OrderResponse create(OrderCreateRequest request) {

        InventoryRequest inventoryRequest = new InventoryRequest(request);
        //对redis进行操作
        SingleResponse<Boolean> decreaseResult = inventoryFacadeService.decrease(inventoryRequest);

        if (decreaseResult.getSuccess()) {
            return orderService.createAndAsyncConfirm(request);
        }
        throw new OrderException(OrderErrorCode.INVENTORY_DECREASE_FAILED);
    }

    @Override
    public OrderResponse confirm(OrderConfirmRequest request) {
        GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest();
        goodsSaleRequest.setUserId(request.getBuyerId());
        goodsSaleRequest.setGoodsId(Long.valueOf(request.getGoodsId()));
        goodsSaleRequest.setGoodsType(request.getGoodsType().name());
        goodsSaleRequest.setIdentifier(request.getOrderId());
        goodsSaleRequest.setQuantity(request.getItemCount());
        BaseResponse response = goodsFacadeService.sale(goodsSaleRequest);

        if (response.getSuccess()) {
            return orderService.confirm(request);
        }

        return new OrderResponse.OrderResponseBuilder().orderId(request.getOrderId()).buildFail(response.getResponseCode(), response.getResponseMessage());
    }
}
