package org.example.inventory.service.impl;

import org.example.inventory.constant.BizErrorCode;
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
}
