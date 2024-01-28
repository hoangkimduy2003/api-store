package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorReponsitory extends JpaRepository<Color, Long> {
}
