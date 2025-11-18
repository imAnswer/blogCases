package org.example.inventory.response;

import lombok.Getter;
import lombok.Setter;
import org.example.inventory.constant.GoodsType;

/**
 * @author liushaoya
 * @since 2025-10-29 10:24
 */
@Getter
@Setter
public class InventoryResponse extends BaseResponse {

    private String goodsId;

    private GoodsType goodsType;

    private String identifier;

    private Integer inventory;
}
