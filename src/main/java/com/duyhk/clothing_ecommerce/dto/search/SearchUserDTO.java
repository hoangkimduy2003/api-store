package com.duyhk.clothing_ecommerce.dto.search;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SearchUserDTO {
    private String phoneNumber;
    private String fullName;
    private Integer status;
    private Integer page;
    private Integer size;
}
