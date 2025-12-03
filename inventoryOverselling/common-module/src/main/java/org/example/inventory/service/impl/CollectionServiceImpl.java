package org.example.inventory.service.impl;

import cn.hutool.core.lang.Assert;
import org.example.inventory.entity.Collection;
import org.example.inventory.exception.CollectionException;
import org.example.inventory.mapper.CollectionInventoryStreamMapper;
import org.example.inventory.mapper.CollectionMapper;
import org.example.inventory.request.GoodsCancelSaleRequest;
import org.example.inventory.request.GoodsTrySaleRequest;
import org.example.inventory.service.CollectionService;
import org.example.inventory.stream.CollectionInventoryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.inventory.exception.CollectionErrorCode.COLLECTION_SAVE_FAILED;
import static org.example.inventory.exception.CollectionErrorCode.COLLECTION_STREAM_SAVE_FAILED;

/**
 * @author liushaoya
 * @since 2025-11-14 19:53
 */
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionInventoryStreamMapper collectionInventoryStreamMapper;
    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean sale(GoodsTrySaleRequest request) {
        //流水校验
        CollectionInventoryStream existStream = collectionInventoryStreamMapper.selectByIdentifier(request.getIdentifier(), request.getGoodsId());
        if (null != existStream) {
            return true;
        }

        //查询出最新的值
        Collection collection = collectionMapper.selectById(request.getGoodsId());

        //新增collection流水
        CollectionInventoryStream stream = new CollectionInventoryStream(collection, request.getIdentifier(), request.getQuantity());
        collectionInventoryStreamMapper.insert(stream);

        //核心逻辑执行
        collectionMapper.sale(request.getGoodsId(), request.getQuantity());
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean cancel(GoodsCancelSaleRequest request) {
        //流水校验
        CollectionInventoryStream existStream = collectionInventoryStreamMapper.selectByIdentifier(request.getIdentifier(), request.getCollectionId());
        if (null != existStream) {
            return true;
        }

        //查询出最新的值
        Collection collection = collectionMapper.selectById(request.getCollectionId());

        //新增collection流水
        CollectionInventoryStream stream = new CollectionInventoryStream(collection, request.getIdentifier(), request.getQuantity());
        int result = collectionInventoryStreamMapper.insert(stream);
        Assert.isTrue(result > 0, () -> new CollectionException(COLLECTION_STREAM_SAVE_FAILED));

        //核心逻辑执行
        result = collectionMapper.cancel(request.getCollectionId(), request.getQuantity());
        Assert.isTrue(result == 1, () -> new CollectionException(COLLECTION_SAVE_FAILED));
        return true;
    }
}
