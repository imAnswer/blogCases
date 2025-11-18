package org.example.inventory.convertor;

import javax.annotation.processing.Generated;
import org.example.inventory.constant.GoodsType;
import org.example.inventory.stream.CollectionInventoryStream;
import org.example.inventory.vo.GoodsStreamVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T23:33:22+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
public class GoodsStreamConvertorImpl implements GoodsStreamConvertor {

    @Override
    public GoodsStreamVO mapToVo(CollectionInventoryStream request) {
        if ( request == null ) {
            return null;
        }

        GoodsStreamVO goodsStreamVO = new GoodsStreamVO();

        if ( request.getCollectionId() != null ) {
            goodsStreamVO.setGoodsId( request.getCollectionId() );
        }
        if ( request.getStreamType() != null ) {
            goodsStreamVO.setStreamType( request.getStreamType() );
        }
        if ( request.getIdentifier() != null ) {
            goodsStreamVO.setIdentifier( request.getIdentifier() );
        }
        if ( request.getChangedQuantity() != null ) {
            goodsStreamVO.setChangedQuantity( request.getChangedQuantity() );
        }
        if ( request.getPrice() != null ) {
            goodsStreamVO.setPrice( request.getPrice() );
        }
        if ( request.getQuantity() != null ) {
            goodsStreamVO.setQuantity( request.getQuantity().longValue() );
        }
        if ( request.getSaleableInventory() != null ) {
            goodsStreamVO.setSaleableInventory( request.getSaleableInventory() );
        }
        if ( request.getFrozenInventory() != null ) {
            goodsStreamVO.setFrozenInventory( request.getFrozenInventory() );
        }
        if ( request.getExtendInfo() != null ) {
            goodsStreamVO.setExtendInfo( request.getExtendInfo() );
        }

        goodsStreamVO.setGoodsType( GoodsType.COLLECTION );

        return goodsStreamVO;
    }
}
