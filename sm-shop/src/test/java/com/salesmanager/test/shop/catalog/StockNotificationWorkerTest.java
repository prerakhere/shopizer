package com.salesmanager.test.shop.catalog;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.salesmanager.core.business.services.catalog.product.StockNotificationService;
import com.salesmanager.core.model.catalog.product.StockNotification;
import com.salesmanager.shop.store.facade.product.StockNotificationWorker;

public class StockNotificationWorkerTest {

    @Mock
    private StockNotificationService stockNotificationService;

    @InjectMocks
    private StockNotificationWorker worker;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void notifySubscribers_withPending_marksAllNotified() {
        StockNotification n1 = new StockNotification();
        n1.setEmail("a@example.com");
        StockNotification n2 = new StockNotification();
        n2.setEmail("b@example.com");

        when(stockNotificationService.findPendingByProduct(1L)).thenReturn(Arrays.asList(n1, n2));

        worker.notifySubscribers(1L);

        verify(stockNotificationService, times(1)).markNotified(n1);
        verify(stockNotificationService, times(1)).markNotified(n2);
    }

    @Test
    public void notifySubscribers_noPending_doesNothing() {
        when(stockNotificationService.findPendingByProduct(1L)).thenReturn(Collections.emptyList());

        worker.notifySubscribers(1L);

        verify(stockNotificationService, never()).markNotified(org.mockito.ArgumentMatchers.any());
    }
}
