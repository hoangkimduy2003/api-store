package com.duyhk.clothing_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill")
public class Bill extends TimeAuditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billCode;
    private String note;
    @Column(name = "total_product")
    private Long tatolProduct;
    private Double totalMoney;
    private Integer status;
    private String phoneNumber;
    private String addressDetail;
    private String fullName;
    private String staff;
    private Integer billType;
    @CreatedDate
    @Column(updatable = false)
    private Date orderDate;
    private Date orderDateFinal;
    private Double moneyRoot;
    private String voucher;
    private Double giaGiam;
    private Long shippingFee;
    private String reasonCancel;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne
    @JoinColumn(name = "exchange_id")
    private Exchange exchange;
}
