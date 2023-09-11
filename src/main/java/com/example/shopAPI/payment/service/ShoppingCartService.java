package com.example.shopAPI.payment.service;

import com.example.shopAPI.mail.dto.EmailDetails;

public interface ShoppingCartService {
    public String add(String email, String productId);
    public String remove(String email, String productId);
    public String pay(String email);
}
