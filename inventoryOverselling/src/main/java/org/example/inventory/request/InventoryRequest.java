package org.example.inventory.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.vo.TradeOrderVO;

import javax.validation.constraints.NotNull;

/**
 * @author liushaoya
 * @since 2025-10-29 10:25
 */
@Getter
@Setter
@NoArgsConstructor
public class InventoryRequest extends BaseRequest {

    /**
     * 商品ID
     */
    @NotNull(message = "goods is null")
    private String goodsId;

    /**
     * 商品ID
     */
    @NotNull(message = "goodsType is null")
    private GoodsType goodsType;

    /**
     * 唯一标识
     */
    private String identifier;

    /**
     * 库存数量
     */
    private Integer inventory;

    public InventoryRequest(OrderCreateRequest orderCreateRequest) {
        this.goodsId = orderCreateRequest.getGoodsId();
        this.goodsType = orderCreateRequest.getGoodsType();
        this.identifier = orderCreateRequest.getOrderId();
        this.inventory = orderCreateRequest.getItemCount();
    }

    public InventoryRequest(CollectionAirDropRequest request) {
        this.goodsId = request.getCollectionId().toString();
        this.goodsType = GoodsType.COLLECTION;
        this.identifier = request.getIdentifier();
        this.inventory = request.getQuantity();
    }

    public InventoryRequest(TradeOrderVO tradeOrderVO) {
        this.setGoodsId(tradeOrderVO.getGoodsId());
        this.setInventory(tradeOrderVO.getItemCount());
        this.setIdentifier(tradeOrderVO.getOrderId());
        this.setGoodsType(tradeOrderVO.getGoodsType());
    }
}
