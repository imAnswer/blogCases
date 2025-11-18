package org.example.inventory.service;

import org.example.inventory.response.SingleResponse;
import org.example.inventory.vo.CollectionVO;

/**
 * 藏品门面服务
 *
 * @author liushaoya
 */
public interface CollectionReadFacadeService {

    /**
     * 根据Id查询藏品
     *
     * @param collectionId
     * @return
     */
    public SingleResponse<CollectionVO> queryById(Long collectionId);

}
