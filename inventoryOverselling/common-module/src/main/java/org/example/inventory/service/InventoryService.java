package org.example.inventory.service;

/**
 * @author liushaoya
 * @since 2025-10-29 10:22
 */

import org.example.inventory.request.InventoryRequest;
import org.example.inventory.response.InventoryResponse;

import java.util.List;

/**
 * 库存服务
 *
 * @author Hollis
 */
public interface InventoryService {

    /**
     * 初始化藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse init(InventoryRequest request);

    /**
     * 扣减藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse decrease(InventoryRequest request);

    /**
     * 增加藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse increase(InventoryRequest request);

    /**
     * 失效藏品库存
     *
     * @param request
     * @return
     */
    public void invalid(InventoryRequest request);

    /**
     * 获取藏品库存
     *
     * @param request
     * @return
     */
    public Integer getInventory(InventoryRequest request);

    /**
     * 获取藏品库存扣减日志
     *
     * @param request
     * @return
     */
    public String getInventoryDecreaseLog(InventoryRequest request);

    /**
     * 获取藏品库存增加日志
     *
     * @param request
     * @return
     */
    public String getInventoryIncreaseLog(InventoryRequest request);

    /**
     * 获取藏品全部日志
     * @param request
     * @return
     */
    public List<String> getInventoryDecreaseLogs(InventoryRequest request);

    /**
     * 移除库存操作日志
     * @param request
     * @return
     */
    public Long removeInventoryDecreaseLog(InventoryRequest request);
}
