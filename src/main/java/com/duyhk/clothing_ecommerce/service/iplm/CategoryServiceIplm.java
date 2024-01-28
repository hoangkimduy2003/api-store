package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.CategoryDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Category;
import com.duyhk.clothing_ecommerce.reponsitory.CategoryReponsitory;
import com.duyhk.clothing_ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceIplm implements CategoryService {
    @Autowired
    private CategoryReponsitory categoryRepo;

    @Override
    public Category convertToEntity(CategoryDTO categoryDTO) {
        return new ModelMapper().map(categoryDTO, Category.class);
    }

    @Override
    public CategoryDTO convertToDto(Category category) {
        return new ModelMapper().map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepo.findAll().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<CategoryDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Category> pageEntity = categoryRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<CategoryDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<CategoryDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public CategoryDTO getById(Long id) {
        CategoryDTO categoryDTO = convertToDto(categoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id không hợp lệ", null)));
        return categoryDTO;
    }

    @Override
    public void create(CategoryDTO categoryDTO) {
        categoryRepo.save(convertToEntity(categoryDTO));
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category =  categoryRepo.findById(categoryDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if(category != null){
            category = convertToEntity(categoryDTO);
            categoryRepo.save(category);
        }
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (category != null) {
            categoryRepo.deleteById(id);
        }
    }
}
