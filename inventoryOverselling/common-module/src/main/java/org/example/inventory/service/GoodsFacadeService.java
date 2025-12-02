package org.example.inventory.service;

import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.request.GoodsSaleRequest;
import org.example.inventory.response.GoodsSaleResponse;
import org.example.inventory.vo.BaseGoodsVO;
import org.example.inventory.vo.GoodsStreamVO;

/**
 * 商品服务
 * @author liushaoya
 * @since 2025-11-14 16:21
 */
public interface GoodsFacadeService {

    /**
     * 获取商品
     *
     * @param goodsId
     * @param goodsType
     * @return
     */
    BaseGoodsVO getGoods(String goodsId, GoodsType goodsType);

    /**
     * 获取商品流水
     *
     * @param goodsId
     * @param goodsType
     * @param goodsEvent
     * @param identifier
     * @return
     */
    GoodsStreamVO getGoodsInventoryStream(String goodsId, GoodsType goodsType, GoodsEvent goodsEvent, String identifier);

    /**
     * 藏品出售的try阶段，做库存占用
     *
     * @param request
     * @return
     */
    GoodsSaleResponse sale(GoodsSaleRequest request);

    /**
     * 藏品出售的cancel阶段，做库存退还
     *
     * @param request
     * @return
     */
    GoodsSaleResponse cancelSale(GoodsSaleRequest request);

}
