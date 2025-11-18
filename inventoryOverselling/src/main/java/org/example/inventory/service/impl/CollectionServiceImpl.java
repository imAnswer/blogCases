package org.example.inventory.service.impl;

import org.example.inventory.entity.Collection;
import org.example.inventory.mapper.CollectionInventoryStreamMapper;
import org.example.inventory.mapper.CollectionMapper;
import org.example.inventory.request.GoodsTrySaleRequest;
import org.example.inventory.service.CollectionService;
import org.example.inventory.stream.CollectionInventoryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
