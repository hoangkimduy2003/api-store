package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailReponsitory extends JpaRepository<ProductDetail, Long> {

    @Query("select p from ProductDetail p where p.product.id = :id")
    List<ProductDetail> findByProduct(@Param("id") Long id);

    void deleteByProductId(Long id);

    @Query("select p from ProductDetail p " +
            "where p.quantity > 0 and p.name like :name " +
            "and (:productDetailCode is null or p.productDetailCode like :productDetailCode)" +
            "and (:status is null or :status = -1 or p.status = :status) " +
            "and (:categoryId is null or :categoryId = -1L or p.product.category.id = :categoryId) " +
            "and (:brandId is null or :brandId = -1L or p.product.brand.id = :brandId) ")
    Page<ProductDetail> search(@Param("name") String name,
                               @Param("productDetailCode") String productDetailCode,
                               @Param("brandId") Long brandId,
                               @Param("status") Integer status,
                               @Param("categoryId") Long categoryId,
                               Pageable pageable);

    ProductDetail findProductDetailByProductDetailCode(String productDetailCode);
}
