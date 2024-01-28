package com.duyhk.clothing_ecommerce.dto.search;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchUserDTO {
    private String phoneNumber;
    private Integer page;
    private Integer size;
}
