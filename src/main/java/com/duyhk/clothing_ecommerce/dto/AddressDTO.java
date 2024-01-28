package com.duyhk.clothing_ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO extends TimeAuditableDTO  implements Serializable {
    private Long id;
    @NotNull(message = "Tên không được để trống")
    @NotBlank(message = "Tên không được để trống")
    private String fullName;
    @NotNull(message = "Địa chỉ không được để trống")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String addressDetail;
    @NotNull(message = "Số điện thoại không được để trống")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String phoneNumber;

    private Integer city;
    private Integer district;
    private Integer ward;
    private String addressFinal;

    private UserDTO user;
}
