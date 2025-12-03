package org.example.inventory.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import org.example.inventory.constant.BizErrorCode;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.InventoryResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.InventoryFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-11-14 16:40
 */
@Service
public class InventoryFacadeServiceImpl implements InventoryFacadeService {

    @Autowired
    private CollectionInventoryRedisService collectionInventoryRedisService;

    private Cache<String, Boolean> soldOutGoodsLocalCache;

    /**
     * 分隔符
     */
    public static final String SEPARATOR = "_";

    @Override
    public SingleResponse<Integer> queryInventory(InventoryRequest inventoryRequest) {

        Integer inventory = collectionInventoryRedisService.getInventory(inventoryRequest);

        if (inventory == null) {
            return SingleResponse.fail(BizErrorCode.DUPLICATED.getCode(), BizErrorCode.DUPLICATED.getMessage());
        }
        return SingleResponse.of(inventory);
    }

    @Override
    public SingleResponse<Boolean> decrease(InventoryRequest inventoryRequest) {

        //执行lua脚本，redis库存扣减以及redis流水插入
        InventoryResponse inventoryResponse = collectionInventoryRedisService.decrease(inventoryRequest);

        if (!inventoryResponse.getSuccess()) {
            return SingleResponse.fail(inventoryResponse.getResponseCode(), inventoryResponse.getResponseMessage());
        }

        return SingleResponse.of(true);
    }

    @Override
    public SingleResponse<Long> removeInventoryDecreaseLog(InventoryRequest inventoryRequest) {
        Long inventoryResponse = collectionInventoryRedisService.removeInventoryDecreaseLog(inventoryRequest);
        return SingleResponse.of(inventoryResponse);
    }

    @Override
    public SingleResponse<Boolean> increase(InventoryRequest inventoryRequest) {
        GoodsType goodsType = inventoryRequest.getGoodsType();
        InventoryResponse inventoryResponse = collectionInventoryRedisService.increase(inventoryRequest);

        if (inventoryResponse.getSuccess()) {

            //如果库存大于0，则清除本地缓存中的商品售罄标记
            //但是因为是本地缓存，所以无法保证一致性，极端情况下，会存在一分钟的数据不一致的延迟。但是在高并发秒杀场景下，一般是不允许修改库存，所以这种不一致业务上可接受
            if (inventoryResponse.getInventory() != null && inventoryResponse.getInventory() > 0) {
                soldOutGoodsLocalCache.invalidate(goodsType + SEPARATOR + inventoryRequest.getGoodsId());
            }

            return SingleResponse.of(true);
        }

        return SingleResponse.fail(inventoryResponse.getResponseCode(), inventoryResponse.getResponseMessage());
    }
}
