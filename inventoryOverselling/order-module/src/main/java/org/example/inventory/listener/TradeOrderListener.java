package org.example.inventory.listener;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.constant.TradeOrderEvent;
import org.example.inventory.constant.TradeOrderState;
import org.example.inventory.exception.TradeErrorCode;
import org.example.inventory.exception.TradeException;
import org.example.inventory.mq.MessageBody;
import org.example.inventory.request.*;
import org.example.inventory.response.GoodsSaleResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.GoodsFacadeService;
import org.example.inventory.service.InventoryFacadeService;
import org.example.inventory.service.OrderFacadeService;
import org.example.inventory.vo.TradeOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static org.example.inventory.exception.TradeErrorCode.INVENTORY_ROLLBACK_FAILED;

@Slf4j
@Component
public class TradeOrderListener {

    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Autowired
    private OrderFacadeService orderFacadeService;

    public static final String ROCKET_MQ_MESSAGE_ID = "ROCKET_MQ_MESSAGE_ID";

    public static final String ROCKET_TAGS = "ROCKET_TAGS";

    public static final String ROCKET_MQ_TOPIC = "ROCKET_MQ_TOPIC";

    /**
     * 从msg中解析出消息对象
     *
     * @param msg
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getMessage(Message<MessageBody> msg, Class<T> type) {
        String messageId = msg.getHeaders().get(ROCKET_MQ_MESSAGE_ID, String.class);
        String tag = msg.getHeaders().get(ROCKET_TAGS, String.class);
        String topic = msg.getHeaders().get(ROCKET_MQ_TOPIC, String.class);
        Object object = JSON.parseObject(msg.getPayload().getBody(), type);
        log.info("Received Message topic:{} messageId:{},object:{}，tag:{}", topic, messageId, JSON.toJSONString(object), tag);
        return (T) object;
    }

    @Bean
    Consumer<Message<MessageBody>> orderClose() {
        return msg -> {
            String closeType = msg.getHeaders().get("CLOSE_TYPE", String.class);
            BaseOrderUpdateRequest orderUpdateRequest;
            if (TradeOrderEvent.CANCEL.name().equals(closeType)) {
                orderUpdateRequest = getMessage(msg, OrderCancelRequest.class);
            } else if (TradeOrderEvent.TIME_OUT.name().equals(closeType)) {
                orderUpdateRequest = getMessage(msg, OrderTimeoutRequest.class);
            } else {
                throw new UnsupportedOperationException("unsupported closeType " + closeType);
            }

            SingleResponse<TradeOrderVO> response = orderFacadeService.getTradeOrder(orderUpdateRequest.getOrderId());
            if (!response.getSuccess()) {
                log.error("getTradeOrder failed,orderCloseRequest:{} , orderQueryResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(response));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
            TradeOrderVO tradeOrderVO = response.getData();
            if (response.getData().getOrderState() != TradeOrderState.CLOSED) {
                log.error("trade order state is illegal ,orderCloseRequest:{} , tradeOrderVO : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(tradeOrderVO));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            GoodsSaleRequest goodsSaleRequest = new GoodsSaleRequest(tradeOrderVO);
            GoodsSaleResponse cancelSaleResult = goodsFacadeService.cancelSale(goodsSaleRequest);
            if (!cancelSaleResult.getSuccess()) {
                log.error("cancelSale failed,orderCloseRequest:{} , collectionSaleResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(cancelSaleResult));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }

            InventoryRequest collectionInventoryRequest = new InventoryRequest(tradeOrderVO);
            SingleResponse<Boolean> decreaseResponse = inventoryFacadeService.increase(collectionInventoryRequest);
            if (decreaseResponse.getSuccess()) {
                log.info("increase success,collectionInventoryRequest:{}", collectionInventoryRequest);
            } else {
                log.error("increase inventory failed,orderCloseRequest:{} , decreaseResponse : {}", JSON.toJSONString(orderUpdateRequest), JSON.toJSONString(decreaseResponse));
                throw new TradeException(INVENTORY_ROLLBACK_FAILED);
            }
        };
    }

}
