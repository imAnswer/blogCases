package org.example.inventory.stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.inventory.constant.CollectionStateEnum;
import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.entity.BaseEntity;
import org.example.inventory.entity.Collection;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 藏品库存流水信息
 * @author liushaoya
 * @since 2025-10-30 21:23
 */
@Getter
@Setter
@NoArgsConstructor
public class CollectionInventoryStream extends BaseEntity {

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
     * 藏品id
     */
    private Long collectionId;

    /**
     * '价格'
     */
    private BigDecimal price;

    /**
     * '藏品数量'
     */
    private Integer quantity;

    /**
     * '可售库存'
     */
    private Long saleableInventory;

    /**
     * '已占库存'
     * @deprecated 这个字段不再使用，详见 CollecitonSerivce.confirmSale
     */
    @Deprecated
    private Long occupiedInventory;

    /**
     * '冻结库存'
     */
    private Long frozenInventory;

    /**
     * '状态'
     */
    private CollectionStateEnum state;

    /**
     * 扩展信息
     */
    private String extendInfo;

    @SuppressWarnings("AliDeprecation")
    public CollectionInventoryStream(Collection collection, String identifier, Integer quantity) {
        this.collectionId = collection.getId();
        this.price = collection.getPrice();
        this.quantity = collection.getQuantity();
        this.saleableInventory = collection.getSaleableInventory();
        ///  被废弃字段： this.occupiedInventory = collection.getOccupiedInventory();
        this.frozenInventory = collection.getFrozenInventory();
        this.state = collection.getState();
        this.identifier = identifier;
        this.changedQuantity = quantity;
        super.setLockVersion(collection.getLockVersion());
        super.setDeleted(collection.getDeleted());
    }

    @JSONField(serialize = false)
    public Long getHeldCollectionId() {
        if (this.extendInfo != null) {
            return ((Integer)JSON.parseObject(this.extendInfo, HashMap.class).get("heldCollectionId")).longValue();
        }

        return null;
    }
}
