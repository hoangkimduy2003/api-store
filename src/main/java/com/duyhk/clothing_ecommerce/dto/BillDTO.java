package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO extends TimeAuditableDTO implements Serializable {
    private Long id;
    private String billCode;
    private String note;
    private Long tatolProduct;
    private Double totalMoney;
    private String phoneNumber;
    private String addressDetail;
    private String fullName;
    private Integer status;
    private Date orderDate;
    private Date orderDateFinal;
    private AddressDTO address;
    private UserDTO user;
    private String staff;
    private Integer billType;
}
