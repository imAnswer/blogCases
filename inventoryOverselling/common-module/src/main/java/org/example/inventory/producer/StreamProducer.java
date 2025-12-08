package org.example.inventory.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.mq.MessageBody;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Component
public class StreamProducer {

    @Resource
    private StreamBridge streamBridge;


    public boolean send(String bindingName, String tag, String msg, String headerKey, String headerValue) {
        // 构建消息对象
        MessageBody message = new MessageBody()
                .setIdentifier(UUID.randomUUID().toString())
                .setBody(msg);
        log.info("send message : {} , {}", bindingName, JSON.toJSONString(message));
        boolean result = streamBridge.send(bindingName, MessageBuilder.withPayload(message).setHeader("TAGS", tag).setHeader(headerKey, headerValue)
                .build());
        log.info("send result : {} , {}", bindingName, result);
        return result;
    }
}

