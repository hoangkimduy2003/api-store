package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.BrandDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand convertToEntity(BrandDTO brandDTO);

    BrandDTO convertToDto(Brand brand);

    List<BrandDTO> getAll();

    PageDTO<List<BrandDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    BrandDTO getById(Long id);

    void create(BrandDTO brandDTO);

    void update(BrandDTO brandDTO);

    void delete(Long id);
}
