package com.duyhk.clothing_ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoucherDTO {
    private Long id;
    private String name;
    private String voucherCode;
    private Double maximumPromotion;
    private Double promotionalLevel;
    private Double minimumInvoice;
    private Double discount;
    private Date dateStart;
    private Date dateEnd;
    private Long quantity;
    private Integer voucherType;
    private Integer status;
}
