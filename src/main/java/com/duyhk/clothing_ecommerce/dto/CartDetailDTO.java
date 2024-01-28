package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO implements Serializable {
    private Long id;
    private Long quantity;
    private ProductDetailDTO productDetail;
    private CartDTO cart;
}
