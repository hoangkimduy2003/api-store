package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.BrandDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ResponseDTO;
import com.duyhk.clothing_ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseDTO<List<BrandDTO>> getAll() {
        return ResponseDTO.<List<BrandDTO>>builder()
                .data(brandService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<BrandDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<BrandDTO>>>builder()
                .data(brandService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<BrandDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<BrandDTO>builder()
                .status(200)
                .data(brandService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody BrandDTO brandDTO) {
        brandService.create(brandDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm thương hiệu thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody BrandDTO brandDTO,
                                    @PathVariable Long id) {
        brandDTO.setId(id);
        brandService.update(brandDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa thương hiệu thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá thương hiệu thành công")
                .build();
    }
}
