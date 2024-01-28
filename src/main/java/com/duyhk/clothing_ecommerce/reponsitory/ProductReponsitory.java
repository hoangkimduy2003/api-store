package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Product;
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
            "and (:categoryId is null or :categoryId = -1L or p.category.id = :categoryId)")
    Page<Product> search(@Param("name") String name,
                         @Param("brandId") Long brandId,
                         @Param("status") Integer status,
                         @Param("categoryId") Long categoryId,
                         Pageable pageable);
}
