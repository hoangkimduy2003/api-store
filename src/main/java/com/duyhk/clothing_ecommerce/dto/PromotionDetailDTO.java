package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetailDTO implements Serializable {
    private Long id;
    private Long promotionId;
    private ProductDTO product;
    private Long quantity;
    private Integer status;
}
