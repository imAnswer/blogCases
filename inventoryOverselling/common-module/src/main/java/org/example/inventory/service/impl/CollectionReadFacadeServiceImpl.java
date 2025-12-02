package org.example.inventory.service.impl;

import org.example.inventory.constant.GoodsType;
import org.example.inventory.convertor.CollectionConvertor;
import org.example.inventory.entity.Collection;
import org.example.inventory.mapper.CollectionMapper;
import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.CollectionReadFacadeService;
import org.example.inventory.service.InventoryFacadeService;
import org.example.inventory.vo.CollectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.example.inventory.exception.CollectionErrorCode.COLLECTION_NOT_EXIST;

/**
 * @author liushaoya
 * @since 2025-11-14 16:26
 */
@Service
public class CollectionReadFacadeServiceImpl implements CollectionReadFacadeService {
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private InventoryFacadeService inventoryFacadeService;
    @Override
    public SingleResponse<CollectionVO> queryById(Long collectionId) {
        Collection collection = collectionMapper.selectById(collectionId);
        if (collection == null) {
            return SingleResponse.fail(COLLECTION_NOT_EXIST.getCode(), COLLECTION_NOT_EXIST.getMessage());
        }

        InventoryRequest request = new InventoryRequest();
        request.setGoodsId(collectionId.toString());
        request.setGoodsType(GoodsType.COLLECTION);
        SingleResponse<Integer> response = inventoryFacadeService.queryInventory(request);

        //没查到的情况下，默认用数据库里面的库存做兜底
        Integer inventory = collection.getSaleableInventory().intValue();
        if (response.getSuccess()) {
            inventory = response.getData();
        }

        CollectionVO collectionVO = CollectionConvertor.INSTANCE.mapToVo(collection);
        collectionVO.setInventory(inventory.longValue());
        collectionVO.setState(collection.getState(), collection.getSaleTime(), inventory.longValue());

        return SingleResponse.of(collectionVO);
    }
}
