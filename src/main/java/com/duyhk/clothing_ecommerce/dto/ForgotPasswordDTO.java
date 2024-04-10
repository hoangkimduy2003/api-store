package com.duyhk.clothing_ecommerce.dto;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
    private String phoneNumber;
    private String email;
}
