package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.FavouriteDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDetailDTO;
import com.duyhk.clothing_ecommerce.entity.Favourite;

import java.util.List;

public interface FavouriteService {
    Favourite convertToEntity(FavouriteDTO favouriteDTO);

    FavouriteDTO convertToDto(Favourite favourite);

    List<FavouriteDTO> getAll();

    PageDTO<List<FavouriteDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    FavouriteDTO getById(Long id);

    void create(FavouriteDTO favouriteDTO);

    void update(FavouriteDTO favouriteDTO);

    void delete(Long id);
}
