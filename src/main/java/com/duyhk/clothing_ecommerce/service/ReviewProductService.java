package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ReviewProductDTO;
import com.duyhk.clothing_ecommerce.dto.SizeDTO;
import com.duyhk.clothing_ecommerce.entity.ReviewProduct;

import java.util.List;

public interface ReviewProductService {
    ReviewProduct convertToEntity(ReviewProductDTO reviewProductDTO);

    ReviewProductDTO convertToDto(ReviewProduct reviewProduct);

    List<ReviewProductDTO> getAll();

    PageDTO<List<ReviewProductDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    ReviewProductDTO getById(Long id);

    void create(ReviewProductDTO reviewProductDTO);

    void update(ReviewProductDTO reviewProductDTO);

    void delete(Long id);
}
