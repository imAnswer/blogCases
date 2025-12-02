package org.example.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.inventory.constant.GoodsEvent;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-11-14 19:49
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseGoodsRequest extends BaseRequest {

    /**
     * 幂等号
     */
    @NotNull(message = "identifier is not null")
    private String identifier;

    /**
     * '藏品id'
     */
    private Long goodsId;

    /**
     * 藏品类型
     *
     * @link GoodsType
     */
    private String goodsType;

    /**
     * 获取事件类型
     *
     * @return
     */
    public abstract GoodsEvent getEventType();
}
