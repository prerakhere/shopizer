package com.salesmanager.shop.model.catalog.product;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class StockNotificationRequest {

    @NotBlank
    @Email
    private String email;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
