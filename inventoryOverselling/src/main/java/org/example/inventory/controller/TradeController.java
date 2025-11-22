package org.example.inventory.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory.constant.BusinessCode;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.idUtil.DistributeID;
import org.example.inventory.idUtil.WorkerIdHolder;
import org.example.inventory.param.BuyParam;
import org.example.inventory.request.InventoryCheckRequest;
import org.example.inventory.request.InventoryRequest;
import org.example.inventory.request.OrderCreateRequest;
import org.example.inventory.response.InventoryCheckResponse;
import org.example.inventory.response.OrderResponse;
import org.example.inventory.service.GoodsFacadeService;
import org.example.inventory.service.InventoryCheckFacadeService;
import org.example.inventory.service.InventoryFacadeService;
import org.example.inventory.service.OrderFacadeService;
import org.example.inventory.vo.BaseGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 基于SpringEvent+InventoryHint实现订单确认自动推进
 * @author liushaoya
 * @since 2025-11-14 16:02
 */
@RestController
@Slf4j
public class TradeController {
    private static ThreadFactory inventoryBypassVerifyThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("inventory-bypass-verify-pool-%d").build();

    private ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(10, inventoryBypassVerifyThreadFactory);

    @Autowired
    private GoodsFacadeService goodsFacadeService;
    @Autowired
    private OrderFacadeService orderFacadeService;
    @Autowired
    private InventoryCheckFacadeService inventoryCheckFacadeService;
    @Autowired
    private InventoryFacadeService inventoryFacadeService;

    @PostMapping("/buy")
    public String buy(@Valid BuyParam buyParam) {
        OrderCreateRequest orderCreateRequest = getOrderCreateRequest(buyParam);
        //创建订单，主要完成两件事
        //1.对Redis进行预扣减
        //2.创建一个异步事件完成订单确认
        OrderResponse orderResponse = orderFacadeService.create(orderCreateRequest);
        if (orderResponse.getSuccess()) {
            InventoryRequest inventoryRequest = new InventoryRequest(orderCreateRequest);
            inventoryBypassVerify(inventoryRequest);
            return orderCreateRequest.getOrderId();
        }
        return "失败";

    }



    private OrderCreateRequest getOrderCreateRequest(BuyParam buyParam) {
        String userId = "29";
        String orderId = DistributeID.generateWithSnowflake(BusinessCode.TRADE_ORDER, WorkerIdHolder.WORKER_ID, userId);
        //创建订单
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setOrderId(orderId);
        //这里的幂等号应该是根据TreadLocal传递过来的，为了实现方便，采用mock的方式写入
        orderCreateRequest.setIdentifier("123456");
        orderCreateRequest.setBuyerId(userId);
        orderCreateRequest.setGoodsId(buyParam.getGoodsId());
        orderCreateRequest.setGoodsType(GoodsType.valueOf(buyParam.getGoodsType()));
        orderCreateRequest.setItemCount(buyParam.getItemCount());
        BaseGoodsVO goodsVO = goodsFacadeService.getGoods(buyParam.getGoodsId(), GoodsType.valueOf(buyParam.getGoodsType()));
        orderCreateRequest.setItemPrice(goodsVO.getPrice());
        orderCreateRequest.setSellerId(goodsVO.getSellerId());
        orderCreateRequest.setGoodsName(goodsVO.getGoodsName());
        orderCreateRequest.setGoodsPicUrl(goodsVO.getGoodsPicUrl());
        orderCreateRequest.setSnapshotVersion(goodsVO.getVersion());
        orderCreateRequest.setOrderAmount(orderCreateRequest.getItemPrice().multiply(new BigDecimal(orderCreateRequest.getItemCount())));

        return orderCreateRequest;
    }

    /**
     * 库存扣减旁路验证
     *
     * @param inventoryRequest
     */
    private void inventoryBypassVerify(InventoryRequest inventoryRequest) {
        try {
            //延迟3秒检查数据库中是否有库存扣减记录
            scheduler.schedule(() -> {
                InventoryCheckRequest inventoryCheckRequest = new InventoryCheckRequest();
                inventoryCheckRequest.setIdentifier(inventoryRequest.getIdentifier());
                inventoryCheckRequest.setGoodsType(inventoryRequest.getGoodsType());
                inventoryCheckRequest.setGoodsId(inventoryRequest.getGoodsId());
                inventoryCheckRequest.setGoodsEvent(GoodsEvent.TRY_SALE);
                inventoryCheckRequest.setChangedQuantity(inventoryRequest.getInventory());
                InventoryCheckResponse checkResponse = inventoryCheckFacadeService.check(inventoryCheckRequest);
                //核验成功,数据一致
                if (checkResponse.getSuccess() && checkResponse.getCheckResult()) {
                    //删除库存扣减流水记录
                    inventoryFacadeService.removeInventoryDecreaseLog(inventoryRequest);
                }
            }, 3, TimeUnit.SECONDS);

        } catch (Exception e) {
            //核验失败打印日志，不影响主流程，等异步任务再核对
            log.error("inventoryBypassVerify failed,", e);
        }
    }

}
