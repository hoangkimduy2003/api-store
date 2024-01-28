package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartReponsitory extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
}
