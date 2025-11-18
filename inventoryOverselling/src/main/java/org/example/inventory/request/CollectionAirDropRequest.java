package org.example.inventory.request;

import lombok.*;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsSaleBizType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-10-29 10:28
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CollectionAirDropRequest extends BaseCollectionRequest {

    /**
     * 接收用户ID
     */
    @NotNull(message = "recipientUserId 不能为空")
    private String recipientUserId;

    /**
     * 数量
     */
    @Min(value = 1, message = "数量不能小于1")
    private Integer quantity;

    /**
     * 商品类型
     */
    @NotNull(message = "bizType 不能为空")
    private GoodsSaleBizType bizType;

    @Override
    public GoodsEvent getEventType() {
        return GoodsEvent.AIR_DROP;
    }
}
