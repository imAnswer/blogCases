package org.example.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 库存扣减问题（超卖问题）
 * @author liushaoya
 * @since 2025-10-29 9:46
 */
@SpringBootApplication
public class InventoryOversellingApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryOversellingApplication.class, args);
    }
}
