package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.PromotionDTO;
import com.duyhk.clothing_ecommerce.dto.ReviewProductDTO;
import com.duyhk.clothing_ecommerce.entity.Promotion;

import java.util.List;

public interface PromotionService {
    Promotion convertToEntity(PromotionDTO promotionDTO);

    PromotionDTO convertToDto(Promotion promotion);

    List<PromotionDTO> getAll();

    PageDTO<List<PromotionDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PromotionDTO getById(Long id);

    void create(PromotionDTO promotion);

    void update(PromotionDTO promotion);

    void changeStatus (PromotionDTO promotionDTO);

    void delete(Long id);
}
