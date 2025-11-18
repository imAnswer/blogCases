package org.example.inventory.service;

import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.SingleResponse;

/**
 * @author liushaoya
 * @since 2025-11-14 16:39
 */
public interface InventoryFacadeService {
    /**
     * 查询库存
     *
     * @param inventoryRequest
     * @return
     */
    public SingleResponse<Integer> queryInventory(InventoryRequest inventoryRequest);

    /**
     * 库存扣减
     *
     * @param inventoryRequest
     * @return
     */
    public SingleResponse<Boolean> decrease(InventoryRequest inventoryRequest);

    /**
     * 移除流水
     *
     * @param inventoryRequest
     * @return
     */
    public SingleResponse<Long> removeInventoryDecreaseLog(InventoryRequest inventoryRequest);
}
