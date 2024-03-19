package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface BillReponsitory extends JpaRepository<Bill, Long> {

    @Query("select b from Bill b where (b.status = 1) and b.billType = 1 order by b.createdAt desc")
    Page<Bill> getSellAtStore(Pageable pageable);
    @Query("select b from Bill b where (:status is null or b.status = :status) order by b.createdAt desc")
    Page<Bill> getByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("select b from Bill b where 1 = 1 and (:status is null or b.status = :status)")
    Page<Bill> searchBill(Pageable pageable, @Param("status") Integer status);

    @Query("select b from Bill b where 1 = 1 " +
            "and (:status is null or b.status = :status) " +
            "and (:startDate is null or b.createdAt >= :startDate) " +
            "and (:endDate is null or b.createdAt <= :endDate) " +
            "and (:staff is null or b.staff like :staff) " +
            "and (:phoneNumber is null or b.phoneNumber like :phoneNumber)  order by b.createdAt desc")
    Page<Bill> search(Pageable pageable,
                      @Param("status") Integer status,
                      @Param("startDate")Date startDate,
                      @Param("endDate")Date endDate,
                      @Param("staff")String staff,
                      @Param("phoneNumber")String phoneNumber);

    @Query("select b from Bill b where 1 = 1 " +
            "and (:status is null or :status = -1 or b.status = :status) " +
            "and (:startDate is null or b.createdAt >= :startDate) " +
            "and (:endDate is null or b.createdAt <= :endDate) " +
            "and (:staff is null or :staff = '%%' or b.staff like :staff) " +
            "and (:phoneNumber is null or :phoneNumber = '%%' or b.phoneNumber like :phoneNumber) " +
            "and (:billType is null or :billType = -1 or b.billType = :billType) order by b.createdAt desc")
    Page<Bill> searchAtStore(Pageable pageable,
                      @Param("status") Integer status,
                      @Param("startDate")Date startDate,
                      @Param("endDate")Date endDate,
                      @Param("staff")String staff,
                      @Param("phoneNumber")String phoneNumber,
                             @Param("billType")Integer billType);
}
