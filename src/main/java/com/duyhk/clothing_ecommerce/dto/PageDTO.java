package com.duyhk.clothing_ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO<T> {
    private Integer totalPages;
    private Long totalElements;
    private T data;
    private Integer status;
    private String msg;
}
