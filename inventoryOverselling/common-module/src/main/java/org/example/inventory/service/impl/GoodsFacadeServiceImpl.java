package org.example.inventory.service.impl;

import org.example.inventory.constant.GoodsEvent;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.convertor.GoodsStreamConvertor;
import org.example.inventory.mapper.CollectionInventoryStreamMapper;
import org.example.inventory.request.GoodsCancelSaleRequest;
import org.example.inventory.request.GoodsSaleRequest;
import org.example.inventory.request.GoodsTrySaleRequest;
import org.example.inventory.response.GoodsSaleResponse;
import org.example.inventory.response.SingleResponse;
import org.example.inventory.service.CollectionReadFacadeService;
import org.example.inventory.service.CollectionService;
import org.example.inventory.service.GoodsFacadeService;
import org.example.inventory.stream.CollectionInventoryStream;
import org.example.inventory.vo.BaseGoodsVO;
import org.example.inventory.vo.CollectionVO;
import org.example.inventory.vo.GoodsStreamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2025-11-14 16:22
 */
@Service
public class GoodsFacadeServiceImpl implements GoodsFacadeService {
    @Autowired
    private CollectionInventoryStreamMapper collectionInventoryStreamMapper;

    @Autowired
    private CollectionReadFacadeService collectionReadFacadeService;

    @Autowired
    private CollectionService collectionService;

    @Override
    public BaseGoodsVO getGoods(String goodsId, GoodsType goodsType) {
        SingleResponse<CollectionVO> response = collectionReadFacadeService.queryById(Long.valueOf(goodsId));
        return response.getData();
    }

    @Override
    public GoodsStreamVO getGoodsInventoryStream(String goodsId, GoodsType goodsType, GoodsEvent goodsEvent, String identifier) {
        CollectionInventoryStream collectionInventoryStream = collectionInventoryStreamMapper.selectByIdentifier(identifier, Long.valueOf(goodsId));

        return GoodsStreamConvertor.INSTANCE.mapToVo(collectionInventoryStream);

    }

    @Override
    public GoodsSaleResponse sale(GoodsSaleRequest request) {
        GoodsTrySaleRequest goodsTrySaleRequest = new GoodsTrySaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());
        GoodsType goodsType = GoodsType.valueOf(request.getGoodsType());

        Boolean trySaleResult = collectionService.sale(goodsTrySaleRequest);

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(trySaleResult);
        return response;
    }

    @Override
    public GoodsSaleResponse cancelSale(GoodsSaleRequest request) {
        GoodsCancelSaleRequest goodsCancelSaleRequest = new GoodsCancelSaleRequest(request.getIdentifier(), request.getGoodsId(), request.getQuantity());

        Boolean result = collectionService.cancel(goodsCancelSaleRequest);

        GoodsSaleResponse response = new GoodsSaleResponse();
        response.setSuccess(result);
        return response;
    }
}
