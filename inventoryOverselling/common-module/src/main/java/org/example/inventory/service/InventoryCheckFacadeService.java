package org.example.inventory.service;

import org.example.inventory.request.InventoryCheckRequest;
import org.example.inventory.response.InventoryCheckResponse;

/**
 * @author liushaoya
 * @since 2025-11-14 17:04
 */
public interface InventoryCheckFacadeService {

    /**
     * 库存核对
     *
     * @param request
     * @return
     */
    public InventoryCheckResponse check(InventoryCheckRequest request);
}
