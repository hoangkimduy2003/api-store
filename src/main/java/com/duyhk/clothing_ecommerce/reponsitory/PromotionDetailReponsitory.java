package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionDetailReponsitory extends JpaRepository<PromotionDetail, Long> {
}
