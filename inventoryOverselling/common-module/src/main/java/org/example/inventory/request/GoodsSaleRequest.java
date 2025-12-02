package org.example.inventory.request;

import lombok.*;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsSaleBizType;

import java.math.BigDecimal;

/**
 * @author liushaoya
 * @since 2025-11-14 19:49
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSaleRequest extends BaseGoodsRequest {

    /**
     * 藏品名称
     */
    private String name;

    /**
     * 藏品封面
     */
    private String cover;

    /**
     * 购入价格
     */
    private BigDecimal purchasePrice;

    /**
     * '持有人id'
     */
    private String userId;

    /**
     * 销售数量
     */
    private Integer quantity;

    /**
     * 业务单号
     */
    private String bizNo;

    /**
     * 业务类型
     *
     * @see GoodsSaleBizType
     */
    private String bizType;


    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.SALE;
    }

}
