package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDetailReponsitory extends JpaRepository<BillDetail, Long> {
    @Query("select b from BillDetail b where b.bill.id = :idBill")
    Page<BillDetail> getByBillId(@Param("idBill") Long id, Pageable pageable) ;
    @Query("select b from BillDetail b where b.bill.id = :idBill")
    List<BillDetail> getByBillIdNoPage(@Param("idBill") Long id) ;

    List<BillDetail> findByBillId(Long id);
}
