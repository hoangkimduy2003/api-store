package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeDTO extends TimeAuditableDTO implements Serializable {
    @Min(value = 0,message = "Vui lòng chọn kích thước")
    private Long id;
    private String name;
    private Integer status;
}
