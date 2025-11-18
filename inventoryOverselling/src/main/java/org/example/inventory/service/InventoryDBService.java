package org.example.inventory.service;

import org.example.inventory.request.GoodsTrySaleRequest;

/**
 * @author liushaoya
 * @since 2025-10-30 21:11
 */
public interface InventoryDBService {
    Boolean sale(GoodsTrySaleRequest request);
}
