package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductReponsitory extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.status = 1 and p.totalQuantity > 0  order by p.totalQuantitySold desc ")
    Page<Product> getByBestSeller(Pageable pageable);

    @Query("select p from Product p where p.status = 1 and p.totalQuantity > 0 order by p.createdAt desc ")
    Page<Product> getByNew(Pageable pageable);

    @Query("select p from Product p where p.name like :name " +
            "and (:brandId is null or :brandId = -1L or p.brand.id = :brandId)" +
            "and (:status is null or :status = -1 or p.status = :status)" +
            "and (:categoryId is null or :categoryId = -1L or p.category.id = :categoryId) order by p.createdAt desc ")
    Page<Product> search(@Param("name") String name,
                         @Param("brandId") Long brandId,
                         @Param("status") Integer status,
                         @Param("categoryId") Long categoryId,
                         Pageable pageable);

    @Query("select p from Product p " +
            "where p.totalQuantity > 0 and p.status = 1 and p.name like :name " +
            "and (:status is null or :status = -1 or p.status = :status) " +
            "and (:categoryId is null or :categoryId = -1L or p.category.id = :categoryId) " +
            "and (:brandId is null or :brandId = -1L or p.brand.id = :brandId) " +
            "and (:sizeId is null or :sizeId = -1L or :sizeId in (select pd.size.id from ProductDetail pd where pd.product.id = p.id)) " +
            "and (:colorId is null or :colorId = -1L or :colorId in (select pd.color.id from ProductDetail pd where pd.product.id = p.id)) " +
            " order by " +
            "case when :oder_by = 'p.totalQuantitySold desc' then p.totalQuantitySold " +
            "when :oder_by = 'p.createAt desc' then p.createdAt " +
            "when :oder_by = 'p.priceSale' then p.priceSale " +
            "when :oder_by = 'p.priceSale desc' then p.priceSale " +
            "else p.id " +
            "end desc ")
    Page<Product> searchOnlineDescending(@Param("name") String name,
                               @Param("brandId") Long brandId,
                               @Param("status") Integer status,
                               @Param("categoryId") Long categoryId,
                               @Param("sizeId") Long sizeId,
                               @Param("colorId") Long colorId,
                               @Param("oder_by") String nameColumnOrderBy,
                               Pageable pageable);

    @Query("select p from Product p " +
            "where p.totalQuantity > 0 and p.status = 1 and p.name like :name " +
            "and (:status is null or :status = -1 or p.status = :status) " +
            "and (:categoryId is null or :categoryId = -1L or p.category.id = :categoryId) " +
            "and (:brandId is null or :brandId = -1L or p.brand.id = :brandId) " +
            "and (:sizeId is null or :sizeId = -1L or :sizeId in (select pd.size.id from ProductDetail pd where pd.product.id = p.id)) " +
            "and (:colorId is null or :colorId = -1L or :colorId in (select pd.color.id from ProductDetail pd where pd.product.id = p.id)) " +
            " order by " +
            "case when :oder_by = 'p.totalQuantitySold desc' then p.totalQuantitySold " +
            "when :oder_by = 'p.createAt desc' then p.createdAt " +
            "when :oder_by = 'p.priceSale' then p.priceSale " +
            "when :oder_by = 'p.priceSale desc' then p.priceSale " +
            "else p.id " +
            "end ")
    Page<Product> searchOnlineUpDescending(@Param("name") String name,
                                         @Param("brandId") Long brandId,
                                         @Param("status") Integer status,
                                         @Param("categoryId") Long categoryId,
                                         @Param("sizeId") Long sizeId,
                                         @Param("colorId") Long colorId,
                                         @Param("oder_by") String nameColumnOrderBy,
                                         Pageable pageable);
}
