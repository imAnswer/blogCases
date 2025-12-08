package org.example.inventory.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.inventory.constant.UserType;
import org.example.inventory.request.CancelParam;
import org.example.inventory.request.OrderCancelRequest;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.service.OrderFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderFacadeService orderFacadeService;

    /**
     * 取消订单
     *
     * @param
     * @return 是否成功
     */
    @PostMapping("/cancel")
    public Boolean cancel(@Valid @RequestBody CancelParam cancelParam) {
        String userId = "29";

        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        //因为每个订单只会被关闭一次，因此使用订单号作为幂等号
        orderCancelRequest.setIdentifier(cancelParam.getOrderId());
        orderCancelRequest.setOperateTime(new Date());
        orderCancelRequest.setOrderId(cancelParam.getOrderId());
        orderCancelRequest.setOperator(userId);
        orderCancelRequest.setOperatorType(UserType.CUSTOMER);

        OrderResponse orderResponse = orderFacadeService.cancel(orderCancelRequest);

        if (orderResponse.getSuccess()) {
            return true;
        }

        return false;
    }
}
