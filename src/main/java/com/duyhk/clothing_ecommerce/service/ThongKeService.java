package com.duyhk.clothing_ecommerce.service;


import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.dto.thongKe.BillRate;
import com.duyhk.clothing_ecommerce.dto.thongKe.SearchThongKeDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;

import java.util.List;

public interface ThongKeService {
    List<BillFinal> getSumFinal(SearchThongKeDTO searchThongKeDTO);

    BillRate getRate(SearchThongKeDTO searchThongKeDTO);

    BillRate getBills(SearchThongKeDTO searchThongKeDTO);

    BillRate getProducts(SearchThongKeDTO searchThongKeDTO);
}
