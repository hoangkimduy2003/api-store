package com.duyhk.clothing_ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    private Integer page;
    private Integer size;
}
