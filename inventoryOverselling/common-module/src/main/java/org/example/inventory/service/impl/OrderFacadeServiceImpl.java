package org.example.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import org.example.inventory.constant.OrderErrorCode;
import org.example.inventory.convertor.TradeOrderConvertor;
import org.example.inventory.entity.TradeOrder;
import org.example.inventory.exception.OrderException;
import org.example.inventory.producer.StreamProducer;
import org.example.inventory.request.*;
import org.example.inventory.response.BaseResponse;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.*;
import org.example.inventory.vo.TradeOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

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
    @Autowired
    private StreamProducer streamProducer;
    @Autowired
    private OrderReadService orderReadService;


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

    @Override
    public OrderResponse cancel(OrderCancelRequest request) {
        return sendTransactionMsgForClose(request);
    }

    @Override
    public SingleResponse<TradeOrderVO> getTradeOrder(String orderId) {
        return SingleResponse.of(TradeOrderConvertor.INSTANCE.mapToVo(orderReadService.getOrder(orderId)));
    }

    @NotNull
    private OrderResponse sendTransactionMsgForClose(BaseOrderUpdateRequest request) {
        /**
         * 订单取消涉及两个事情：
         *  1.订单取消
         *  2.数据回滚（包括redis的数据和数据库的数据）
         *  因此这里要用到RockerMQ的分布式事务
         */
        //因为RocketMQ 的事务消息中，如果本地事务发生了异常，这里返回也会是个 true，所以就需要做一下反查进行二次判断，才能知道关单操作是否成功
        //消息监听：TradeOrderListener
        streamProducer.send("orderClose-out-0", null, JSON.toJSONString(request), "CLOSE_TYPE", request.getOrderEvent().name());
        TradeOrder tradeOrder = orderReadService.getOrder(request.getOrderId());
        OrderResponse orderResponse = new OrderResponse();
        if (tradeOrder.isClosed()) {
            orderResponse.setSuccess(true);
        } else {
            orderResponse.setSuccess(false);
        }
        return orderResponse;
    }
}
