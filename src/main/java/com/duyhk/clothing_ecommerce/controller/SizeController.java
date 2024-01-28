package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("")
    public ResponseDTO<List<SizeDTO>> getAll() {
        return ResponseDTO.<List<SizeDTO>>builder()
                .data(sizeService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<SizeDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<SizeDTO>>>builder()
                .data(sizeService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<SizeDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<SizeDTO>builder()
                .status(200)
                .data(sizeService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody SizeDTO sizeDTO) {
        sizeService.create(sizeDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm kích cỡ thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody SizeDTO sizeDTO,
                                    @PathVariable Long id) {
        sizeDTO.setId(id);
        sizeService.update(sizeDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa kích cỡ thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        sizeService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá kích cỡ thành công")
                .build();
    }
}
