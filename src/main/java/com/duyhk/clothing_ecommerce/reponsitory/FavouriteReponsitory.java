package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteReponsitory extends JpaRepository<Favourite, Long> {
}
