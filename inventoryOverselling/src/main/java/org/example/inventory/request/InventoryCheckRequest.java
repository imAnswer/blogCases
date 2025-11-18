package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsType;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-11-14 17:03
 */
@Getter
@Setter
public class InventoryCheckRequest extends BaseRequest {

    /**
     * '商品ID
     */
    @NotNull(message = "goodsId不能为空")
    private String goodsId;

    /**
     * '商品类型'
     */
    @NotNull(message = "goodsType不能为空")
    private GoodsType goodsType;

    /**
     * '标识符'
     */
    @NotNull(message = "identifier不能为空")
    private String identifier;

    /**
     * '变更数量'
     */
    @NotNull(message = "changedQuantity不能为空")
    private Integer changedQuantity;

    /**
     * '商品事件'
     */
    @NotNull(message = "goodsEvent不能为空")
    private GoodsEvent goodsEvent;
}

