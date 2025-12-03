package org.example.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory.constant.GoodsEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCancelSaleRequest {

    private String identifier;
    private Long collectionId;
    private Integer quantity;


    public GoodsEvent eventType() {
        return GoodsEvent.CANCEL_SALE;
    }
}

