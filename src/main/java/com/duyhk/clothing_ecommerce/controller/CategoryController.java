package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseDTO<List<CategoryDTO>> getAll() {
        return ResponseDTO.<List<CategoryDTO>>builder()
                .data(categoryService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<CategoryDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                    @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<CategoryDTO>>>builder()
                .data(categoryService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CategoryDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<CategoryDTO>builder()
                .status(200)
                .data(categoryService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm loại sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO,
                                           @PathVariable Long id) {
        categoryDTO.setId(id);
        categoryService.update(categoryDTO);
        return ResponseDTO.<CategoryDTO>builder()
                .data(categoryDTO)
                .status(200)
                .msg("Sửa loại sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá loại sản phẩm thành công")
                .build();
    }
}
