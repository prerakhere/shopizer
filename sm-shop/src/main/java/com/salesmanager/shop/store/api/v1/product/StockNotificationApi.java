package com.salesmanager.shop.store.api.v1.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.business.services.catalog.product.StockNotificationService;
import com.salesmanager.core.model.catalog.product.StockNotification;
import com.salesmanager.shop.model.catalog.product.StockNotificationRequest;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Stock Notification Api" })
@SwaggerDefinition(tags = { @Tag(name = "Stock Notification Api", description = "Subscribe to back-in-stock notifications") })
public class StockNotificationApi {

    @Autowired
    private StockNotificationService stockNotificationService;

    /**
     * Public endpoint — any customer can subscribe
     */
    @PostMapping("/product/{productId}/notify-stock")
    @ResponseStatus(HttpStatus.CREATED)
    public StockNotification subscribe(
            @PathVariable Long productId,
            @RequestBody StockNotificationRequest request) {
        try {
            return stockNotificationService.subscribe(productId, request.getEmail());
        } catch (IllegalArgumentException e) {
            throw new com.salesmanager.shop.store.api.exception.RestApiException("400", e.getMessage());
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error subscribing to stock notification", e);
        }
    }

    /**
     * Admin endpoint — view all pending subscribers for a product
     */
    @GetMapping("/private/product/{productId}/notifications")
    public List<StockNotification> getSubscribers(@PathVariable Long productId) {
        try {
            return stockNotificationService.findPendingByProduct(productId);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error fetching stock notifications", e);
        }
    }
}
