package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.dto.CartDetailDTO;
import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailReponsitory extends JpaRepository<CartDetail, Long> {
    List<CartDetail> findByCartId(Long id);
    List<CartDetail> findByCartIdAndProductDetailId(Long cartId, Long productDetailId);
}
