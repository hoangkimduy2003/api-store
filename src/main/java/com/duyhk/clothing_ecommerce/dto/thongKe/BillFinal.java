package com.duyhk.clothing_ecommerce.dto.thongKe;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillFinal {
    private Date date;
    private Long totalMoney;
}
