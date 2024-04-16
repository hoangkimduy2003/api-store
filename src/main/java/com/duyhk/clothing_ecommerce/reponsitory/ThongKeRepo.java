package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.dto.thongKe.BillRate;
import com.duyhk.clothing_ecommerce.entity.Bill;
import com.duyhk.clothing_ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ThongKeRepo extends JpaRepository<Bill, Long> {

    @Query("SELECT DATE(b.orderDateFinal),SUM(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDateFinal is not null and b.status = 5 and year(b.orderDateFinal) = :year " +
            "GROUP BY DATE(b.orderDateFinal) " +
            "order by DATE(b.orderDateFinal)")
    List<Object[]> getSumDay(@Param("year") Long year);

    @Query("SELECT month(b.orderDateFinal),SUM(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDateFinal is not null and b.status = 5 and year(b.orderDateFinal) = :year " +
            "GROUP BY month(b.orderDateFinal), year(b.orderDateFinal) " +
            "order by month(b.orderDateFinal)")
    List<Object[]> getSumMonth(@Param("year") Long year);

    @Query("SELECT year(b.orderDateFinal),SUM(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDateFinal is not null and b.status = 5 " +
            "GROUP BY year(b.orderDateFinal) " +
            "order by year(b.orderDateFinal)")
    List<Object[]> getSumYear();

    @Query("SELECT month(b.orderDate),count(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDate is not null and (:billType is null or b.billType = :billType ) and (:year is null or year(b.orderDate) = :year )" +
            "GROUP BY month(b.orderDate) " +
            "order by month(b.orderDate)")
    List<Object[]> getSumBillMonth(@Param("billType") Integer billType, @Param("year") Long year   );

    @Query("SELECT DATE(b.orderDate),count(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDate is not null and (:billType is null or b.billType = :billType ) and (:month is null or month(b.orderDate) = :month) and (:year is null or year(b.orderDate) = :year ) " +
            "GROUP BY DATE(b.orderDate) " +
            "order by DATE(b.orderDate)")
    List<Object[]> getSumBillDay(@Param("billType") Integer billType, @Param("month") Long month, @Param("year") Long year);

    @Query("SELECT year(b.orderDate),count(b.totalMoney) " +
            "FROM Bill b " +
            "where b.orderDate is not null and (:billType is null or b.billType = :billType ) " +
            "GROUP BY year(b.orderDate) " +
            "order by year(b.orderDate)")
    List<Object[]> getSumBillYear(@Param("billType") Integer billType);

    @Query("select COUNT(b.id) from Bill b where b.billType = :billType and b.status = :status")
    long findByBillTypeAndStatus(@Param("billType") Integer billType, @Param("status") Integer status);

    @Query("select COUNT(b.id) from Bill b where b.billType = :billType ")
    long totalBillOnline(@Param("billType") Integer billType);

    @Query("select COUNT(u.id) from Users u")
    long totalUser();

    @Query("select COUNT(u.id) from Users u where u.totalInvoice > 0")
    long totalUserBy();

    @Query("select count(b.id) from Bill b where 1 = 1 and (:billType is null or b.billType = :billType) " +
            "and (:day is null or :day = -1 or day(b.orderDate) = :day)  " +
            "and (:month is null or :month = -1 or month(b.orderDate) = :month) " +
            "and (:year is null or :year = -1 or year(b.orderDate) = :year )")
    long totalBill(@Param("billType") Integer billType, @Param("day") Long day, @Param("month") Long month, @Param("year") Long year);

    @Query("select count(b.id) from Bill b where 1 = 1 " +
            "and (:billType is null or b.billType = :billType) " +
            "and (:status is null or b.status = :status) " +
            "and (:day is null or :day = -1l or DAY(b.createdAt) = :day) " +
            "and (:month is null or :month = -1l or MONTH(b.createdAt) = :month) " +
            "and (:year is null or :year = -1l or YEAR(b.createdAt) = :year)")
    Long totalBill(@Param("billType") Integer billType,
                   @Param("status") Integer status,
                   @Param("day") Long day,
                   @Param("month") Long month,
                   @Param("year") Long year);

    @Query("select SUM(b.totalMoney) from Bill b where b.status = 5")
    Long totalMoneyBill();

    @Query("select SUM(p.totalQuantitySold) from Product p")
    Long totalProductSold();

    @Query("select b from Bill b order by b.createdAt desc")
    Page<Bill> getBillNew(Pageable pageable);

    @Query("select p from Product p order by p.totalQuantitySold desc ")
    Page<Product> getProductSale(Pageable pageable);

    @Query("select SUM(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and YEAR(b.orderDateFinal) = :year ")
    Long totalMoneyYear(@Param("year") Long year);

    @Query("select SUM(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and MONTH(b.orderDateFinal) = :month  and YEAR(b.orderDateFinal) = :year")
    Long totalMoneyMonth(@Param("month") Long month, @Param("year") Long year);

    @Query("select SUM(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and DAY(b.orderDateFinal) = :day  and MONTH(b.orderDateFinal) = :month  and YEAR(b.orderDateFinal) = :year")
    Long totalMoneyDay(@Param("day") Long day, @Param("month") Long month, @Param("year") Long year);

    @Query("select count(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and YEAR(b.orderDateFinal) = :year ")
    Long totalBillYear(@Param("year") Long year);

    @Query("select count(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and MONTH(b.orderDateFinal) = :month and YEAR(b.orderDateFinal) = :year")
    Long totalBillMonth(@Param("month") Long month, @Param("year") Long year);

    @Query("select count(b.totalMoney) from Bill b where 1 = 1 and b.status = 5 and DAY(b.orderDateFinal) = :day and MONTH(b.orderDateFinal) = :month  and YEAR(b.orderDateFinal) = :year")
    Long totalBillDay(@Param("day") Long day, @Param("month") Long month, @Param("year") Long year);

    @Query("select b from Bill b where" +
            " b.status = 5 and DATE(b.orderDateFinal) = DATE(:date) order by b.orderDateFinal desc")
    Page<Bill> billInToDay(Pageable pageable , @Param("date")Date date);


    @Query("select count(p.id) from Product p where 1 = 1 and (:status is null or :status = -1 or p.status = :status) " +
            "and (:quantityMin is null or :quantityMin = -1l or p.totalQuantity <= :quantityMin) " +
            "and (:quantityMax is null or :quantityMax = -1l or p.totalQuantity > :quantityMax) ")
    Long sumProduct(@Param("status") Integer status, @Param("quantityMin") Long quantityMin, @Param("quantityMax") Long quantityMax);

    @Query("select p from Product p where 1 = 1 and p.status = 1 order by p.createdAt desc")
    Page<Product> pageProductNew(Pageable pageable);

    @Query("select p from Product p where 1 = 1 and p.status = 1 order by p.totalQuantitySold desc")
    Page<Product> pageProductSold(Pageable pageable);
}
