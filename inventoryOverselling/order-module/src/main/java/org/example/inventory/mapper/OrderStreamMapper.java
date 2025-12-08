package org.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.inventory.stream.TradeOrderStream;

/**
 * @author liushaoya
 * @since 2025-11-14 19:41
 */
@Mapper
public interface OrderStreamMapper extends BaseMapper<TradeOrderStream> {
    /**
     * 根据流标识查询
     *
     * @param streamIdentifier
     * @param streamType
     * @param orderId
     * @return
     */
    TradeOrderStream selectByIdentifier(String streamIdentifier, String streamType, String orderId);
}
