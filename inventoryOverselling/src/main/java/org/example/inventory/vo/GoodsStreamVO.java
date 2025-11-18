package org.example.inventory.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liushaoya
 * @since 2025-11-14 17:05
 */
@Getter
@Setter
@ToString
public class GoodsStreamVO implements Serializable {
    /**
     * 流水类型
     */
    private GoodsEvent streamType;

    /**
     * '幂等号'
     */
    private String identifier;

    /**
     * '变更数量'
     */
    private Integer changedQuantity;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品类型
     */
    private GoodsType goodsType;

    /**
     * '价格'
     */
    private BigDecimal price;

    /**
     * '数量'
     */
    private Long quantity;

    /**
     * '可售库存'
     */
    private Long saleableInventory;

    /**
     * '冻结库存'
     */
    private Long frozenInventory;

    /**
     * 扩展信息
     */
    private String extendInfo;
}
