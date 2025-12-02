package org.example.inventory.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liushaoya
 * @since 2025-10-30 21:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTrySaleRequest {

    private String identifier;
    private Long goodsId;
    private Integer quantity;

}
