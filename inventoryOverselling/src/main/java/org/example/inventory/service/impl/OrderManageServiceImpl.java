package org.example.inventory.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.constant.OrderErrorCode;
import org.example.inventory.constant.RepoErrorCode;
import org.example.inventory.constant.TradeOrderEvent;
import org.example.inventory.constant.UserType;
import org.example.inventory.entity.TradeOrder;
import org.example.inventory.event.OrderCreateEvent;
import org.example.inventory.exception.BizException;
import org.example.inventory.exception.OrderException;
import org.example.inventory.mapper.OrderMapper;
import org.example.inventory.mapper.OrderStreamMapper;
import org.example.inventory.request.BaseOrderUpdateRequest;
import org.example.inventory.request.OrderConfirmRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.service.OrderManageService;
import org.example.inventory.stream.TradeOrderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static org.example.inventory.constant.OrderErrorCode.ORDER_NOT_EXIST;
import static org.example.inventory.constant.OrderErrorCode.PERMISSION_DENIED;

/**
 * @author liushaoya
 * @since 2025-11-14 17:18
 */
@Service
@Slf4j
public class OrderManageServiceImpl implements OrderManageService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderStreamMapper orderStreamMapper;
    @Autowired
    protected ApplicationContext applicationContext;
    @Autowired
    protected TransactionTemplate transactionTemplate;

    @Override
    @Transactional
    public OrderResponse createAndAsyncConfirm(OrderCreateRequest request) {
        TradeOrder existOrder = orderMapper.selectByIdentifier(request.getIdentifier(), request.getBuyerId());
        //判断订单是否重复创建
        if (existOrder != null) {
            return new OrderResponse.OrderResponseBuilder().orderId(existOrder.getOrderId()).buildSuccess();
        }

        TradeOrder tradeOrder = doCreate(request);

        //创建一个SpringEvent事件实现异步调用
        applicationContext.publishEvent(new OrderCreateEvent(tradeOrder));
        log.info("事件已发布, 线程: {}", Thread.currentThread().getName());
        return new OrderResponse.OrderResponseBuilder().orderId(tradeOrder.getOrderId()).buildSuccess();
    }

    @Override
    public OrderResponse confirm(OrderConfirmRequest request) {
        return doExecute(request, tradeOrder -> tradeOrder.confirm(request));
    }

    private TradeOrder doCreate(OrderCreateRequest request) {
        TradeOrder tradeOrder = TradeOrder.createOrder(request);
        //向订单表中插入订单记录，并且订单状态倍设置为CREATE
        orderMapper.insert(tradeOrder);
        //向流水表中插入order_state为create的流水
        TradeOrderStream orderStream = new TradeOrderStream(tradeOrder, request.getOrderEvent(), request.getIdentifier());
        orderStreamMapper.insert(orderStream);
        return tradeOrder;
    }

    /**
     * 通用订单更新逻辑
     *
     * @param orderRequest
     * @param consumer
     * @return
     */
    protected OrderResponse doExecute(BaseOrderUpdateRequest orderRequest, Consumer<TradeOrder> consumer) {
        OrderResponse response = new OrderResponse();
        return handle(orderRequest, response, "doExecute", request -> {

            TradeOrder existOrder = orderMapper.selectByOrderId(request.getOrderId());
            if (existOrder == null) {
                throw new OrderException(ORDER_NOT_EXIST);
            }

            if (!hasPermission(existOrder, orderRequest.getOrderEvent(), orderRequest.getOperator(), orderRequest.getOperatorType())) {
                throw new OrderException(PERMISSION_DENIED);
            }

            TradeOrderStream existStream = orderStreamMapper.selectByIdentifier(orderRequest.getIdentifier(), orderRequest.getOrderEvent().name(), orderRequest.getOrderId());
            if (existStream != null) {
                return new OrderResponse.OrderResponseBuilder().orderId(existStream.getOrderId()).streamId(existStream.getId().toString()).buildDuplicated();
            }

            //核心逻辑执行
            consumer.accept(existOrder);

            //开启事务
            return transactionTemplate.execute(transactionStatus -> {

                boolean result = orderMapper.updateByOrderId(existOrder) == 1;
                Assert.isTrue(result, () -> new OrderException(OrderErrorCode.UPDATE_ORDER_FAILED));

                TradeOrderStream orderStream = new TradeOrderStream(existOrder, orderRequest.getOrderEvent(), orderRequest.getIdentifier());
                result = orderStreamMapper.insert(orderStream) == 1;
                Assert.isTrue(result, () -> new BizException(RepoErrorCode.INSERT_FAILED));

                return new OrderResponse.OrderResponseBuilder().orderId(orderStream.getOrderId()).streamId(String.valueOf(orderStream.getId())).buildSuccess();
            });
        });
    }

    public static <T, R extends OrderResponse> OrderResponse handle(T request, R response, String method, Function<T, R> function) {
        log.info("before execute method={}, request={}", method, JSON.toJSONString(request));
        try {
            requireNonNull(request);
            response = function.apply(request);
        } catch (OrderException e) {
            log.error(e.toString(), e);
            response.setSuccess(false);
            response.setResponseCode(e.getErrorCode().getCode());
            response.setResponseMessage(e.getErrorCode().getMessage());
            log.error("failed execute method={}, exception={}", method, JSON.toJSONString(e));
        } catch (Exception e) {
            response.setSuccess(false);
            response.setResponseCode("SYSTEM_ERROR");
            response.setResponseMessage(e.getMessage());
            log.error("failed execute method={}, exception={}", method, JSON.toJSONString(e));
        } finally {
            log.info("after execute method={}, result={}", method, JSON.toJSONString(response));
        }
        return response;
    }

    private boolean hasPermission(TradeOrder existOrder, TradeOrderEvent orderEvent, String operator, UserType operatorType) {
        switch (orderEvent) {
            case PAY:
            case CANCEL:
            case CREATE_AND_CONFIRM:
                return existOrder.getBuyerId().equals(operator);
            case TIME_OUT:
            case CONFIRM:
            case FINISH:
            case DISCARD:
                return operatorType == UserType.PLATFORM;
            default:
                throw new UnsupportedOperationException("unsupport order event : " + orderEvent);
        }
    }
}
