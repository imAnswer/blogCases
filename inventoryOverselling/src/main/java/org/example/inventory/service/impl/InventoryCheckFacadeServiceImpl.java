package org.example.inventory.service.impl;

import org.example.inventory.request.InventoryCheckRequest;
import org.example.inventory.response.InventoryCheckResponse;
import org.example.inventory.service.GoodsFacadeService;
import org.example.inventory.service.InventoryCheckFacadeService;
import org.example.inventory.vo.GoodsStreamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-11-14 17:05
 */
@Service
public class InventoryCheckFacadeServiceImpl implements InventoryCheckFacadeService {

    @Autowired
    private GoodsFacadeService goodsFacadeService;

    @Override
    public InventoryCheckResponse check(InventoryCheckRequest request) {
        InventoryCheckResponse response = new InventoryCheckResponse();
        boolean checkResult = doInventoryCheck(request);

        response.setSuccess(true);
        response.setCheckResult(checkResult);
        return response;
    }

    private boolean doInventoryCheck(InventoryCheckRequest inventoryCheckRequest) {
        GoodsStreamVO goodsStreamVO = goodsFacadeService.getGoodsInventoryStream(inventoryCheckRequest.getGoodsId(), inventoryCheckRequest.getGoodsType(), inventoryCheckRequest.getGoodsEvent(), inventoryCheckRequest.getIdentifier());
        if (goodsStreamVO == null) {
            return false;
        }

        return goodsStreamVO.getChangedQuantity().equals(inventoryCheckRequest.getChangedQuantity());
    }
}
