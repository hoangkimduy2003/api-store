package com.duyhk.clothing_ecommerce.dto;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO extends TimeAuditableDTO implements Serializable {
    private Long id;
    private String name;
    private Integer status;
}
