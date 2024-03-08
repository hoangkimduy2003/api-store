package com.duyhk.clothing_ecommerce.dto.search;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductDetail {
    private String name;
    private String productDetailCode;
    private Long categoryId;
    private Long brandId;
    private Long colorId;
    private Long sizeId;
    private Integer page;
    private Integer size;
}
