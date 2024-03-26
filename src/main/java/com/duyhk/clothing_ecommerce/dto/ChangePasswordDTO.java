package com.duyhk.clothing_ecommerce.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String passwordConfirm;
}
