package com.salesmanager.core.business.repositories.catalog.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.catalog.product.StockNotification;

public interface StockNotificationRepository extends JpaRepository<StockNotification, Long> {

    List<StockNotification> findByProductIdAndNotified(Long productId, boolean notified);

    Optional<StockNotification> findByProductIdAndEmail(Long productId, String email);
}
