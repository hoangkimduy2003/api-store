package com.duyhk.clothing_ecommerce.dto.thongKe;

import com.duyhk.clothing_ecommerce.entity.Bill;
import com.duyhk.clothing_ecommerce.entity.Product;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class BillRate {
    private Double completionRate;
    private Double cancelRate;
    private Double buyRate;
    private Long totalMoneyBill;
    private Long totalBill;
    private long totalProductSold;
    private long totalUser;
    private List<Bill> billNews;
    private List<Product> productSale;
    private long totalBillDay;
    private long totalBillMonth;
    private long totalBillYear;
    private long totalMoneyDay;
    private long totalMoneyMonth;
    private long totalMoneyYear;
    private Long totalBillOnline;
    private Long totalBillAtStore;
    private Long totalBillOnlineChoXacNhan;
    private Long totalBillOnlineChoGiaoHang;
    private Long totalBillOnlineDangGiao;
    private Long totalBillOnlineDaGiao;
    private Long totalBillOnlineDaHuy;
    private Long totalBillAtStoreCho;
    private Long totalBillAtStoreDaHT;

    private Page<Bill> billToday;

    private List<BillFinal> sumBillStore;
    private List<BillFinal> sumBillOnline;


    private Long sumProduct;
    private Long totalProductSapHet;
    private Long totalProductActive;
    private Long totalProductHet;

    private Page<Product> pageProductNew;
    private Page<Product> pageProductHot;
}
