package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.ReviewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviewProduct")
@CrossOrigin
public class ReviewProductController {
    @Autowired
    private ReviewProductService reviewProductService;

    @GetMapping("")
    public ResponseDTO<List<ReviewProductDTO>> getAll() {
        return ResponseDTO.<List<ReviewProductDTO>>builder()
                .data(reviewProductService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<ReviewProductDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                         @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ReviewProductDTO>>>builder()
                .data(reviewProductService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<ReviewProductDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<ReviewProductDTO>builder()
                .status(200)
                .data(reviewProductService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody ReviewProductDTO reviewProductDTO) {
        reviewProductService.create(reviewProductDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Đánh giá sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody ReviewProductDTO reviewProductDTO,
                                    @PathVariable Long id) {
        reviewProductDTO.setId(id);
        reviewProductService.update(reviewProductDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa đánh giá thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        reviewProductService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá đánh giá thành công")
                .build();
    }
}
