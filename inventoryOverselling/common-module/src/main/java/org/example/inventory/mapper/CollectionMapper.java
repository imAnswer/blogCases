package org.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.inventory.entity.Collection;

/**
 * <p>
 * 藏品信息 Mapper 接口
 * </p>
 *
 */
@Mapper
public interface CollectionMapper extends BaseMapper<Collection> {


    /**
     * 库存扣减
     *
     * @param id
     * @param quantity
     * @return
     */
    int sale(Long id, Integer quantity);

}
