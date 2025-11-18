package org.example.inventory.convertor;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.example.inventory.entity.Collection;
import org.example.inventory.entity.CollectionSnapshot;
import org.example.inventory.request.CollectionCreateRequest;
import org.example.inventory.vo.CollectionVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T23:33:22+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
public class CollectionConvertorImpl implements CollectionConvertor {

    @Override
    public CollectionVO mapToVo(Collection request) {
        if ( request == null ) {
            return null;
        }

        CollectionVO collectionVO = new CollectionVO();

        if ( request.getSaleableInventory() != null ) {
            collectionVO.setInventory( request.getSaleableInventory() );
        }
        if ( request.getId() != null ) {
            collectionVO.setId( request.getId() );
        }
        if ( request.getName() != null ) {
            collectionVO.setName( request.getName() );
        }
        if ( request.getCover() != null ) {
            collectionVO.setCover( request.getCover() );
        }
        if ( request.getPrice() != null ) {
            collectionVO.setPrice( request.getPrice() );
        }
        if ( request.getQuantity() != null ) {
            collectionVO.setQuantity( request.getQuantity().longValue() );
        }
        if ( request.getSaleTime() != null ) {
            collectionVO.setSaleTime( request.getSaleTime() );
        }
        if ( request.getVersion() != null ) {
            collectionVO.setVersion( request.getVersion() );
        }
        if ( request.getBookStartTime() != null ) {
            collectionVO.setBookStartTime( request.getBookStartTime() );
        }
        if ( request.getBookEndTime() != null ) {
            collectionVO.setBookEndTime( request.getBookEndTime() );
        }
        if ( request.getCanBook() != null ) {
            collectionVO.setCanBook( request.getCanBook() );
        }

        collectionVO.setState( setState(request.getState(), request.getSaleTime(), request.getSaleableInventory()) );

        return collectionVO;
    }

    @Override
    public Collection mapToEntity(CollectionVO request) {
        if ( request == null ) {
            return null;
        }

        Collection collection = new Collection();

        if ( request.getInventory() != null ) {
            collection.setSaleableInventory( request.getInventory() );
        }
        if ( request.getId() != null ) {
            collection.setId( request.getId() );
        }
        if ( request.getName() != null ) {
            collection.setName( request.getName() );
        }
        if ( request.getCover() != null ) {
            collection.setCover( request.getCover() );
        }
        if ( request.getPrice() != null ) {
            collection.setPrice( request.getPrice() );
        }
        if ( request.getQuantity() != null ) {
            collection.setQuantity( request.getQuantity().intValue() );
        }
        if ( request.getSaleTime() != null ) {
            collection.setSaleTime( request.getSaleTime() );
        }
        if ( request.getVersion() != null ) {
            collection.setVersion( request.getVersion() );
        }
        if ( request.getBookStartTime() != null ) {
            collection.setBookStartTime( request.getBookStartTime() );
        }
        if ( request.getBookEndTime() != null ) {
            collection.setBookEndTime( request.getBookEndTime() );
        }
        if ( request.getCanBook() != null ) {
            collection.setCanBook( request.getCanBook() );
        }

        return collection;
    }

    @Override
    public CollectionSnapshot createSnapshot(Collection request) {
        if ( request == null ) {
            return null;
        }

        CollectionSnapshot collectionSnapshot = new CollectionSnapshot();

        if ( request.getId() != null ) {
            collectionSnapshot.setCollectionId( request.getId() );
        }
        if ( request.getDeleted() != null ) {
            collectionSnapshot.setDeleted( request.getDeleted() );
        }
        if ( request.getLockVersion() != null ) {
            collectionSnapshot.setLockVersion( request.getLockVersion() );
        }
        if ( request.getName() != null ) {
            collectionSnapshot.setName( request.getName() );
        }
        if ( request.getCover() != null ) {
            collectionSnapshot.setCover( request.getCover() );
        }
        if ( request.getClassId() != null ) {
            collectionSnapshot.setClassId( request.getClassId() );
        }
        if ( request.getPrice() != null ) {
            collectionSnapshot.setPrice( request.getPrice() );
        }
        if ( request.getDetail() != null ) {
            collectionSnapshot.setDetail( request.getDetail() );
        }
        if ( request.getCreateTime() != null ) {
            collectionSnapshot.setCreateTime( request.getCreateTime() );
        }
        if ( request.getSaleTime() != null ) {
            collectionSnapshot.setSaleTime( request.getSaleTime() );
        }
        if ( request.getSyncChainTime() != null ) {
            collectionSnapshot.setSyncChainTime( request.getSyncChainTime() );
        }
        if ( request.getCreatorId() != null ) {
            collectionSnapshot.setCreatorId( request.getCreatorId() );
        }
        if ( request.getVersion() != null ) {
            collectionSnapshot.setVersion( request.getVersion() );
        }

        return collectionSnapshot;
    }

    @Override
    public Collection mapToEntity(CollectionCreateRequest request) {
        if ( request == null ) {
            return null;
        }

        Collection collection = new Collection();

        if ( request.getName() != null ) {
            collection.setName( request.getName() );
        }
        if ( request.getCover() != null ) {
            collection.setCover( request.getCover() );
        }
        if ( request.getPrice() != null ) {
            collection.setPrice( request.getPrice() );
        }
        if ( request.getQuantity() != null ) {
            collection.setQuantity( request.getQuantity().intValue() );
        }
        if ( request.getDetail() != null ) {
            collection.setDetail( request.getDetail() );
        }
        if ( request.getCreateTime() != null ) {
            collection.setCreateTime( request.getCreateTime() );
        }
        if ( request.getSaleTime() != null ) {
            collection.setSaleTime( request.getSaleTime() );
        }
        if ( request.getCreatorId() != null ) {
            collection.setCreatorId( request.getCreatorId() );
        }
        if ( request.getBookStartTime() != null ) {
            collection.setBookStartTime( request.getBookStartTime() );
        }
        if ( request.getBookEndTime() != null ) {
            collection.setBookEndTime( request.getBookEndTime() );
        }
        if ( request.getCanBook() != null ) {
            collection.setCanBook( request.getCanBook() );
        }

        return collection;
    }

    @Override
    public List<CollectionVO> mapToVo(List<Collection> request) {
        if ( request == null ) {
            return null;
        }

        List<CollectionVO> list = new ArrayList<CollectionVO>( request.size() );
        for ( Collection collection : request ) {
            list.add( mapToVo( collection ) );
        }

        return list;
    }
}
