package org.example.inventory.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory.constant.UserType;
import org.example.inventory.entity.TradeOrder;
import org.example.inventory.event.OrderCreateEvent;
import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.service.OrderFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Date;

/**
 * @author liushaoya
 * @since 2025-11-14 19:47
 */
@Component
@Slf4j
public class OrderEventListener {

    @Autowired
    private OrderFacadeService orderFacadeService;

    //@Async括号内定义的是一个线程池，如果不定义，就默认创建只有一个线程的线程池完成下面的逻辑
     @Async("orderListenExecutor")
    // 因为在后面的压测中发现，异步处理会导致整体的订单CONFIRM延迟变长，影响用户体验，所以改为同步调用的方式，详见压测部分视频。
    @TransactionalEventListener(value = OrderCreateEvent.class)
    public void onApplicationEvent(OrderCreateEvent event) {
        log.info("收到事件: {}, 线程: {}", event, Thread.currentThread().getName());
        TradeOrder tradeOrder = (TradeOrder) event.getSource();
        OrderConfirmRequest confirmRequest = new OrderConfirmRequest();
        confirmRequest.setOperator(UserType.PLATFORM.name());
        confirmRequest.setOperatorType(UserType.PLATFORM);
        confirmRequest.setOrderId(tradeOrder.getOrderId());
        confirmRequest.setIdentifier(tradeOrder.getIdentifier());
        confirmRequest.setOperateTime(new Date());
        confirmRequest.setBuyerId(tradeOrder.getBuyerId());
        confirmRequest.setItemCount(tradeOrder.getItemCount());
        confirmRequest.setGoodsType(tradeOrder.getGoodsType());
        confirmRequest.setGoodsId(tradeOrder.getGoodsId());
        orderFacadeService.confirm(confirmRequest);
        log.info("订单确认成功: {}, 线程: {}", tradeOrder.getOrderId(), Thread.currentThread().getName());
    }
}
