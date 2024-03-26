package com.duyhk.clothing_ecommerce.dto.search;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchBillCustomerDTO {
    private Long userId;
    private Integer status;
    private Integer size;
    private Integer page;
}
