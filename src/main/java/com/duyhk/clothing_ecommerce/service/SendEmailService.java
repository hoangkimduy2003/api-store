package com.duyhk.clothing_ecommerce.service;

public interface SendEmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
