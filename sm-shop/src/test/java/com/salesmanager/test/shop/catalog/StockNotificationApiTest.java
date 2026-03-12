package com.salesmanager.test.shop.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.salesmanager.core.model.catalog.product.StockNotification;
import com.salesmanager.shop.application.ShopApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class StockNotificationApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpEntity<String> jsonRequest(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    @Test
    public void subscribe_validEmail_returns201() {
        ResponseEntity<StockNotification> response = restTemplate.postForEntity(
                "/api/v1/product/1/notify-stock",
                jsonRequest("{\"email\":\"notify-test@example.com\"}"),
                StockNotification.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("notify-test@example.com", response.getBody().getEmail());
        assertEquals(Long.valueOf(1L), response.getBody().getProductId());
    }

    @Test
    public void subscribe_invalidEmail_returns400() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/product/1/notify-stock",
                jsonRequest("{\"email\":\"not-an-email\"}"),
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void subscribe_sameEmailTwice_returnsExistingWithoutError() {
        restTemplate.postForEntity("/api/v1/product/1/notify-stock",
                jsonRequest("{\"email\":\"duplicate@example.com\"}"), StockNotification.class);

        ResponseEntity<StockNotification> second = restTemplate.postForEntity(
                "/api/v1/product/1/notify-stock",
                jsonRequest("{\"email\":\"duplicate@example.com\"}"),
                StockNotification.class);

        assertEquals(HttpStatus.CREATED, second.getStatusCode());
    }
}
