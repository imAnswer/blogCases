package org.example.inventory.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-11-14 16:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyParam {
    @NotNull(message = "goodsId is null")
    private String goodsId;

    @NotNull(message = "goodsType is null")
    private String goodsType;

    /**
     * 商品数量
     */
    @Min(value = 1)
    private int itemCount;
}
