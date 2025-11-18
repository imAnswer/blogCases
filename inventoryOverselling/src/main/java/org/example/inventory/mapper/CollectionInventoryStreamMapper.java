package org.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.inventory.stream.CollectionInventoryStream;

/**
 * @author liushaoya
 * @since 2025-10-30 21:26
 */
@Mapper
public interface CollectionInventoryStreamMapper extends BaseMapper<CollectionInventoryStream> {
    /**
     * 根据标识符查询
     *
     * @param identifier
     * @param collectionId
     * @return
     */
    CollectionInventoryStream selectByIdentifier(String identifier, Long collectionId);

}
