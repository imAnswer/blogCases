package org.example.inventory.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.example.inventory.constant.TradeOrderEvent;
import org.example.inventory.constant.TradeOrderState;
import org.example.inventory.entity.TradeOrder;
import org.example.inventory.request.BaseOrderUpdateRequest;
import org.example.inventory.request.OrderCancelRequest;
import org.example.inventory.request.OrderTimeoutRequest;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.service.OrderManageService;
import org.example.inventory.service.OrderReadService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * RocketMQ生产者监听类
 */
@Slf4j
@Component
public class OrderCloseTransactionListener implements TransactionListener {
    @Resource
    private OrderManageService orderManageService;

    @Resource
    private OrderReadService orderReadService;

    /**
     * 在half message发送成功后，调用这个方法执行本地事务
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            Map<String, String> headers = message.getProperties();
            String closeType = headers.get("CLOSE_TYPE");

            OrderResponse response = null;
            if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
                OrderCancelRequest cancelRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderCancelRequest.class);
                log.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(cancelRequest), closeType);
                response = orderManageService.cancel(cancelRequest);

            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                OrderTimeoutRequest timeoutRequest = JSON.parseObject(JSON.parseObject(message.getBody()).getString("body"), OrderTimeoutRequest.class);
                log.info("executeLocalTransaction , baseOrderUpdateRequest = {} , closeType = {}", JSON.toJSONString(timeoutRequest), closeType);
                response = orderManageService.timeout(timeoutRequest);
            } else {
                throw new UnsupportedOperationException("unsupported closeType " + closeType);
            }

            if (response.getSuccess()) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            log.error("executeLocalTransaction error, message = {}", message, e);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 检查本地事务是否成功的回调
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String closeType = messageExt.getProperties().get("CLOSE_TYPE");
        BaseOrderUpdateRequest baseOrderUpdateRequest = null;
        if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
            baseOrderUpdateRequest = JSON.parseObject(JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderCancelRequest.class);
        } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
            baseOrderUpdateRequest = JSON.parseObject(JSON.parseObject(new String(messageExt.getBody())).getString("body"), OrderTimeoutRequest.class);
        }

        TradeOrder tradeOrder = orderReadService.getOrder(baseOrderUpdateRequest.getOrderId());

        if (tradeOrder.getOrderState() == TradeOrderState.CLOSED) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
