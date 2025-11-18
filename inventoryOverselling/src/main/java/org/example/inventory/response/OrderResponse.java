package org.example.inventory.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liushaoya
 * @since 2025-11-14 16:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseResponse{
    private String orderId;

    private String streamId;

    public static class OrderResponseBuilder {
        private String orderId;
        private String streamId;

        public OrderResponseBuilder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderResponseBuilder streamId(String streamId) {
            this.streamId = streamId;
            return this;
        }

        public OrderResponse buildSuccess() {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderId);
            orderResponse.setStreamId(streamId);
            orderResponse.setSuccess(true);
            return orderResponse;
        }

        public OrderResponse buildDuplicated() {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderId);
            orderResponse.setStreamId(streamId);
            orderResponse.setSuccess(true);
            orderResponse.setResponseCode("DUPLICATED");
            orderResponse.setResponseMessage("重复请求");
            return orderResponse;
        }

        public OrderResponse buildFail(String code, String msg) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderId);
            orderResponse.setStreamId(streamId);
            orderResponse.setSuccess(false);
            orderResponse.setResponseCode(code);
            orderResponse.setResponseMessage(msg);
            return orderResponse;
        }
    }
}
