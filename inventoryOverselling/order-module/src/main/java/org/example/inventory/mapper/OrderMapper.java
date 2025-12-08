package org.example.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.inventory.entity.TradeOrder;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-11-14 17:20
 */
@Mapper
public interface OrderMapper extends BaseMapper<TradeOrder> {
    /**
     * 根据幂等号查询订单
     *
     * @param identifier 幂等号
     * @param buyerId    买家ID
     * @return 订单
     */
    TradeOrder selectByIdentifier(String identifier, String buyerId);

    /**
     * 根据订单号查询订单
     *
     * @param orderId 订单号
     * @return 订单
     */
    TradeOrder selectByOrderId(@NotNull String orderId);

    /**
     * 更新订单
     *
     * @param entity 订单
     * @return 影响行数
     */
    int updateByOrderId(TradeOrder entity);

    /**
     * 根据订单号和买家ID查询订单
     *
     * @param orderId 订单号
     * @param buyerId 买家ID
     * @return 订单
     */
    TradeOrder selectByOrderIdAndBuyer(@NotNull String orderId, @NotNull String buyerId);

}
