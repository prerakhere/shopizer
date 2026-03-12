package com.salesmanager.shop.store.facade.product;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.catalog.product.StockNotificationService;
import com.salesmanager.core.model.catalog.product.StockNotification;

@Service
public class StockNotificationWorker {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockNotificationWorker.class);

    @Autowired
    private StockNotificationService stockNotificationService;

    @Async
    public void notifySubscribers(Long productId) {
        List<StockNotification> pending = stockNotificationService.findPendingByProduct(productId);
        for (StockNotification notification : pending) {
            // Mock: log instead of sending real email
            LOGGER.info("📧 MOCK EMAIL: Notifying {} - product {} is back in stock", notification.getEmail(), productId);
            stockNotificationService.markNotified(notification);
        }
        if (!pending.isEmpty()) {
            LOGGER.info("✅ Notified {} subscriber(s) for product {}", pending.size(), productId);
        }
    }
}
