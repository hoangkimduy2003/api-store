package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO implements Serializable {
    private Long id;
    private String productDetailCode;
    private String name;
    private Double price;
    private Double priceSale;
    @Min(value = 1,message = "Vui lòng nhập số lượng")
    @NotNull(message = "Vui lòng nhập số lượng")
    private Long quantity;
    private Long quantitySold;
    private Double averagedReview;
    private SizeDTO size;
    private ColorDTO color;
    private Integer status;
    private ProductDTO product;

    public ProductDetailDTO(Long id) {
        this.id = id;
    }
}
