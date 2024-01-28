package com.duyhk.clothing_ecommerce.reponsitory;

import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.FavouriteDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteDetailReponsitory extends JpaRepository<FavouriteDetail, Long> {
}
