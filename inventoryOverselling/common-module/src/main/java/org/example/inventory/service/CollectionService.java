package org.example.inventory.service;

import org.example.inventory.request.GoodsCancelSaleRequest;
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
    Boolean sale(GoodsTrySaleRequest request);

    /**
     * 取消
     *
     * @param request
     * @return
     */
    Boolean cancel(GoodsCancelSaleRequest request);
}
