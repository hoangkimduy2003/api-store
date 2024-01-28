package com.duyhk.clothing_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    @NotBlank(message = "Vui lòng số điện thoại")
    private String phoneNumber;
    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
}
