package com.salesmanager.core.business.services.catalog.product;

import java.util.List;

import com.salesmanager.core.model.catalog.product.StockNotification;

public interface StockNotificationService {

    StockNotification subscribe(Long productId, String email);

    List<StockNotification> findPendingByProduct(Long productId);

    void markNotified(StockNotification notification);
}
