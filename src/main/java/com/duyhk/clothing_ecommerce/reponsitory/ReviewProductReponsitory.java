package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.PromotionDetail;
import com.duyhk.clothing_ecommerce.entity.ReviewProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewProductReponsitory extends JpaRepository<ReviewProduct, Long> {
}
