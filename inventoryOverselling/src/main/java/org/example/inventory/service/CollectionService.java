package org.example.inventory.service;

import org.example.inventory.request.GoodsTrySaleRequest;

/**
 * @author liushaoya
 * @since 2025-11-14 16:31
 */
public interface CollectionService {
    /**
     * 售卖
     *
     * @param request
     * @return
     */
    public Boolean sale(GoodsTrySaleRequest request);
}
