package org.example.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.inventory.constant.GoodsEvent;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-10-29 9:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseCollectionRequest extends BaseRequest {

    /**
     * 幂等号
     */
    @NotNull(message = "identifier is not null")
    private String identifier;

    /**
     * '藏品id'
     */
    private Long collectionId;

    /**
     * 获取事件类型
     * @return
     */
    public abstract GoodsEvent getEventType();
}
