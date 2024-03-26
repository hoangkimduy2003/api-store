package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThongKeRepo extends JpaRepository<Bill, Long> {

    @Query("SELECT DATE(b.orderDateFinal),SUM(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDateFinal is not null and b.status = 5 " +
            "GROUP BY DATE(b.orderDateFinal) " +
            "order by DATE(b.orderDateFinal)")
    List<Object[]> getSumFinal();
}
