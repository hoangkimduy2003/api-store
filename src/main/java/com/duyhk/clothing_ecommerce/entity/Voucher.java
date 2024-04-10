package com.duyhk.clothing_ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "voucher")
public class Voucher extends TimeAuditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String voucherCode;
    private Double maximumPromotion;
    private Double promotionalLevel;
    private Double minimumInvoice;
    private Double discount;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Long quantity;
    private Integer voucherType;
    private Integer status;
    private Integer sendType;
}
