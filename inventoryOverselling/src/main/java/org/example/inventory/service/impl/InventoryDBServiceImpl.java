package org.example.inventory.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.inventory.entity.Collection;
import org.example.inventory.exception.CollectionException;
import org.example.inventory.mapper.CollectionInventoryStreamMapper;
import org.example.inventory.mapper.CollectionMapper;
import org.example.inventory.request.GoodsTrySaleRequest;
import org.example.inventory.service.InventoryDBService;
import org.example.inventory.stream.CollectionInventoryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.inventory.exception.CollectionErrorCode.COLLECTION_SAVE_FAILED;
import static org.example.inventory.exception.CollectionErrorCode.COLLECTION_STREAM_SAVE_FAILED;

/**
 * @author liushaoya
 * @since 2025-10-30 21:13
 */
@Service
public class InventoryDBServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements InventoryDBService {

    @Autowired
    private CollectionInventoryStreamMapper collectionInventoryStreamMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean sale(GoodsTrySaleRequest request) {
        //流水校验,查询数据库中是否已经存在这条流水
        CollectionInventoryStream existStream = collectionInventoryStreamMapper.selectByIdentifier(request.getIdentifier(), request.getGoodsId());
        if (null != existStream) {
            return true;
        }

        //查询出最新的值
        Collection collection = this.getById(request.getGoodsId());

        //新增collection流水
        CollectionInventoryStream stream = new CollectionInventoryStream(collection, request.getIdentifier(), request.getQuantity());
        int result = collectionInventoryStreamMapper.insert(stream);
        if (!(result > 0)) {
            throw new CollectionException(COLLECTION_STREAM_SAVE_FAILED);
        }

        //核心逻辑执行
        result = collectionMapper.sale(request.getGoodsId(), request.getQuantity());
        if (result != 1) {
            throw new CollectionException(COLLECTION_SAVE_FAILED);
        }

        return true;
    }
}
