package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.CartDetailDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.CartDetail;

import java.util.List;

public interface CartDetailService {
    CartDetail convertToEntity(CartDetailDTO cartDetailDTO);

    CartDetailDTO convertToDto(CartDetail cartDetail);

    List<CartDetailDTO> getAll();

    PageDTO<List<CartDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    CartDetailDTO getById(Long id);

    CartDetailDTO create(CartDetailDTO cartDetailDTO);

    CartDetailDTO update(CartDetailDTO cartDetailDTO);

    void delete(Long id);
}
