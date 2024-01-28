package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.FavouriteDTO;
import com.duyhk.clothing_ecommerce.dto.FavouriteDetailDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.FavouriteDetail;

import java.util.List;

public interface FavouriteDetailService {
    FavouriteDetail convertToEntity(FavouriteDetailDTO favouriteDetailDTO);

    FavouriteDetailDTO convertToDto(FavouriteDetail favouriteDetail);

    List<FavouriteDetailDTO> getAll();

    PageDTO<List<FavouriteDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    FavouriteDetailDTO getById(Long id);

    void create(FavouriteDetailDTO favouriteDetailDTO);

    void update(FavouriteDetailDTO favouriteDetailDTO);

    void delete(Long id);
}
