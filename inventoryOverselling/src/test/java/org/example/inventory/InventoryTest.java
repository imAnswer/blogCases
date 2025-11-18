package org.example.inventory;

import org.example.inventory.constant.BusinessCode;
import org.example.inventory.idUtil.DistributeID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liushaoya
 * @since 2025-11-11 22:33
 */
@SpringBootTest
public class InventoryTest {
    @Test
    public void test() {
        System.out.println(DistributeID.generateWithSnowflake(BusinessCode.TRADE_ORDER, 1, "1"));
    }
}
