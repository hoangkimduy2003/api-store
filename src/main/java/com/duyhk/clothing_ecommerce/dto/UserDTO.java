package com.duyhk.clothing_ecommerce.dto;

import com.duyhk.clothing_ecommerce.entity.Cart;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends TimeAuditableDTO implements Serializable {
    private Long id;
    private String userCode;
    private String phoneNumber;
    private String fullName;
    private String password;
    private String email;
    private String image;
    private Role role;
    private Integer status;
    private Double totalInvoiceValue;
    private Long totalInvoice;
    private Integer userType;
    private FavouriteDTO favourite;

    public UserDTO(Long id){
        this.id = id;
    }
}
