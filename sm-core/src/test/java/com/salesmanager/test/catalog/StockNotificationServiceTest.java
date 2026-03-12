package com.salesmanager.test.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.salesmanager.core.business.repositories.catalog.product.StockNotificationRepository;
import com.salesmanager.core.business.services.catalog.product.StockNotificationServiceImpl;
import com.salesmanager.core.model.catalog.product.StockNotification;

public class StockNotificationServiceTest {

    @Mock
    private StockNotificationRepository repository;

    @InjectMocks
    private StockNotificationServiceImpl service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void subscribe_invalidEmail_throwsIllegalArgument() {
        service.subscribe(1L, "not-an-email");
    }

    @Test
    public void subscribe_newEmail_savesAndReturns() {
        when(repository.findByProductIdAndEmail(1L, "test@example.com")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        StockNotification result = service.subscribe(1L, "test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals(Long.valueOf(1L), result.getProductId());
        assertNotNull(result.getSubscribedDate());
        verify(repository).save(any());
    }

    @Test
    public void subscribe_existingEmail_returnsExistingWithoutDuplicate() {
        StockNotification existing = new StockNotification();
        existing.setEmail("test@example.com");
        existing.setProductId(1L);
        when(repository.findByProductIdAndEmail(1L, "test@example.com")).thenReturn(Optional.of(existing));

        StockNotification result = service.subscribe(1L, "test@example.com");

        assertEquals(existing, result);
        verify(repository, never()).save(any());
    }

    @Test
    public void findPendingByProduct_returnsPendingOnly() {
        StockNotification n = new StockNotification();
        n.setProductId(1L);
        when(repository.findByProductIdAndNotified(1L, false)).thenReturn(Arrays.asList(n));

        List<StockNotification> result = service.findPendingByProduct(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void markNotified_setsNotifiedAndSaves() {
        StockNotification n = new StockNotification();
        n.setNotified(false);

        service.markNotified(n);

        assertEquals(true, n.isNotified());
        assertNotNull(n.getNotifiedDate());
        verify(repository).save(n);
    }
}
