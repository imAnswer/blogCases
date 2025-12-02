package org.example.inventory.controller;

import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.InventoryResponse;
import org.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushaoya
 * @since 2025-10-29 10:41
 */
@RestController
public class CollectionController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/init")
    public InventoryResponse init(InventoryRequest request) {
        return inventoryService.init(request);
    }

    @GetMapping("/decrease")
    public InventoryResponse decrease(InventoryRequest request) {
        return inventoryService.decrease(request);
    }

    @GetMapping("/getInventory")
    public Integer getInventory(InventoryRequest request) {
        return inventoryService.getInventory(request);
    }
}
