package org.example.inventory.producer;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.message.MessageConst;
import org.example.inventory.mq.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class StreamProducer {

    private static Logger logger = LoggerFactory.getLogger(StreamProducer.class);

    public static final int DELAY_LEVEL_1_M = 5;

    public static final int DELAY_LEVEL_30_S = 4;

    public static final String ROCKET_MQ_MESSAGE_ID = "ROCKET_MQ_MESSAGE_ID";

    public static final String ROCKET_TAGS = "ROCKET_TAGS";

    public static final String ROCKET_MQ_TOPIC = "ROCKET_MQ_TOPIC";

    @Resource
    private StreamBridge streamBridge;

    public boolean send(String bingingName, String tag, String msg) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        logger.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));
        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag)
                .build());
        logger.info("send result : {} , {} , {}", bingingName, tag, result);
        return result;
    }

    /**
     * 发送延迟消息
     *
     * @param bingingName
     * @param tag
     * @param msg
     * @param delayLevel  RocketMQ支持18个级别的延迟时间，分别为1s、5s、10s、30s、1m、2m、3m、4m、5m、6m、7m、8m、9m、10m、20m、30m、1h、2h
     * @return
     */
    public boolean send(String bingingName, String tag, String msg, int delayLevel) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        logger.info("send message : {} , {} , {}", bingingName, tag, JSON.toJSONString(message));
        boolean result = streamBridge.send(bingingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag).setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel)
                .build());
        logger.info("send result : {} , {} , {}", bingingName, tag, result);
        return result;
    }

    public boolean send(String bindingName, String tag, String msg, String headerKey, String headerValue) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        logger.info("send message : {} , {}", bindingName, JSON.toJSONString(message));
        boolean result = streamBridge.send(bindingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag).setHeader(headerKey, headerValue)
                .build());
        logger.info("send result : {} , {}", bindingName, result);
        return result;
    }
}

