package com.duyhk.clothing_ecommerce.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BrandDTO implements Serializable {
    @Min(value = 0,message = "Vui lòng chọn thương hiệu")
    private Long id;
    private String name;
    private Integer status;
}
