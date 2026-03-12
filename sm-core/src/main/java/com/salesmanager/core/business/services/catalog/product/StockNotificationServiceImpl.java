package com.salesmanager.core.business.services.catalog.product;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.catalog.product.StockNotificationRepository;
import com.salesmanager.core.model.catalog.product.StockNotification;

@Service
public class StockNotificationServiceImpl implements StockNotificationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    @Autowired
    private StockNotificationRepository repository;

    @Override
    public StockNotification subscribe(Long productId, String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        return repository.findByProductIdAndEmail(productId, email)
                .orElseGet(() -> {
                    StockNotification n = new StockNotification();
                    n.setProductId(productId);
                    n.setEmail(email);
                    n.setSubscribedDate(new Date());
                    return repository.save(n);
                });
    }

    @Override
    public List<StockNotification> findPendingByProduct(Long productId) {
        return repository.findByProductIdAndNotified(productId, false);
    }

    @Override
    public void markNotified(StockNotification notification) {
        notification.setNotified(true);
        notification.setNotifiedDate(new Date());
        repository.save(notification);
    }
}
