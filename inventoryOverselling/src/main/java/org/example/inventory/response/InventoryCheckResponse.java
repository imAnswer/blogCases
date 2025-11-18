package org.example.inventory.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liushaoya
 * @since 2025-11-14 17:03
 */
@Getter
@Setter
public class InventoryCheckResponse extends BaseResponse {

    /**
     * 核对结果
     */
    private Boolean checkResult;
}
