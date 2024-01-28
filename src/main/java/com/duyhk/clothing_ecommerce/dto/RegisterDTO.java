package com.duyhk.clothing_ecommerce.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {
    private String email;
    private String password;
    private String passwordConfirm;
}
