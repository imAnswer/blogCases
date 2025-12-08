package org.example.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.inventory.entity.TradeOrder;

import javax.annotation.Nullable;
import java.util.List;

public interface OrderReadService {
    TradeOrder getOrder(String orderId);

    TradeOrder getOrder(String orderId, String buyerId);

    Page<TradeOrder> pageQueryByState(String buyerId, String state, int currentPage, int pageSize);

    List<TradeOrder> pageQueryTimeoutOrders(int pageSize, @Nullable String buyerIdTailNumber, Long minId);

    List<TradeOrder> pageQueryNeedConfirmOrders(int pageSize, @Nullable String buyerIdTailNumber, Long minId);
}
