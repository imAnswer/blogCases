package org.example.inventory.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liushaoya
 * @since 2025-11-14 19:51
 */
@Getter
@Setter
public class GoodsSaleResponse extends BaseResponse {
    /**
     * 持有藏品id
     */
    private Long heldCollectionId;

    public static class GoodsResponseBuilder {
        private Long heldCollectionId;

        public GoodsSaleResponse.GoodsResponseBuilder heldCollectionId(Long heldCollectionId) {
            this.heldCollectionId = heldCollectionId;
            return this;
        }


        public GoodsSaleResponse buildFail(String code, String msg) {
            GoodsSaleResponse goodsSaleResponse = new GoodsSaleResponse();
            goodsSaleResponse.setHeldCollectionId(heldCollectionId);
            goodsSaleResponse.setSuccess(false);
            goodsSaleResponse.setResponseCode(code);
            goodsSaleResponse.setResponseMessage(msg);
            return goodsSaleResponse;
        }
    }
}
