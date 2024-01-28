package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.CategoryDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    Category convertToEntity(CategoryDTO categoryDTO);

    CategoryDTO convertToDto(Category category);

    List<CategoryDTO> getAll();

    PageDTO<List<CategoryDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    CategoryDTO getById(Long id);

    void create(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(Long id);
}
