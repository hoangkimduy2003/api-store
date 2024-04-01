package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                      @Param("startDate") Date startDate,
                      @Param("endDate") Date endDate,
                      @Param("staff") String staff,
                      @Param("phoneNumber") String phoneNumber);

    @Query("select b from Bill b where 1 = 1 " +
            "and not (b.billType = 1 and b.status = 1) " +
            "and (:status is null or :status = -1 or b.status = :status) " +
            "and (:startDate is null or b.createdAt >= :startDate) " +
            "and (:endDate is null or b.createdAt <= :endDate) " +
            "and (:staff is null or :staff = '%%' or b.staff like :staff) " +
            "and (:phoneNumber is null or :phoneNumber = '%%' or b.phoneNumber like :phoneNumber) " +
            "and (:billType is null or :billType = -1 or b.billType = :billType) order by b.createdAt desc")
    Page<Bill> searchAtStore(Pageable pageable,
                             @Param("status") Integer status,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate,
                             @Param("staff") String staff,
                             @Param("phoneNumber") String phoneNumber,
                             @Param("billType") Integer billType);

    // bieu do
    @Query("select b from Bill b where YEAR(b.orderDateFinal) =:year ")
    List<Bill> chartbyYear(int year);


    // doanh thu
    @Query(value = "SELECT SUM(b.totalMoney) FROM Bill b where b.status=5")
    Double getTotalMoney();

    // san pham da ban
    @Query(value = "select SUM(b.tatolProduct) from Bill b where (b.status=1)")
    int gettotalSanPhamDaBan();

    // hoa don moi tao
    @Query("select b from Bill b  order by b.createdAt desc")
    Page<Bill> getCreaterBill(Pageable pageable);

    // hoa don cho
    @Query("select count(b.id) from Bill b where (b.status=1 or b.status=2 or b.status=3 or b.status=4) ")
    Long pendingInvoice();

    // da giao
    @Query("select count(b.id) from Bill b where ( b.status=5) ")
    Long billTransacted();

    // da huy
    @Query("select count(b.id) from Bill b where ( b.status=0 or b.billType=0) ")
    Long billCancelled();

    //doanh thu ngay
    @Query("select sum(b.totalMoney) From Bill b where b.status=5 and " +
            " DAY(b.orderDateFinal)=:day and" +
            " MONTH(b.orderDateFinal)=:month and " +
            "YEAR(b.orderDateFinal)=:year")
    Double revenueDay(int day,int month,int year);
//
//    // doanh thu thang
    @Query("select sum(b.totalMoney) From Bill b where b.status=5 and " +
            "MONTH(b.orderDateFinal)=:month and " +
            "YEAR(b.orderDateFinal)=:year")
    Double revenueMonth(int month,int year);

    @Query("select sum(b.totalMoney) From Bill b where b.status=5 and " +
            "YEAR(b.orderDateFinal) =:year")
    Double revenueYear(int year);

    @Query("SELECT MONTH(b.orderDateFinal) as date FROM Bill b where YEAR(b.orderDateFinal)=:year and " +
            "b.status=5" +
            " group by MONTH(b.orderDateFinal)" +
            "ORDER BY MONTH(b.orderDateFinal)  ASC")
    List<ArrayList<Integer>> getMonth(int year);

    @Query("SELECT sum(b.totalMoney) FROM Bill b where YEAR(b.orderDateFinal)=:year and " +
            "b.status=5" +
            " group by MONTH(b.orderDateFinal)" +
            "ORDER BY MONTH(b.orderDateFinal)  ASC")
    List<ArrayList<Integer>> getMoney(int year);
}
