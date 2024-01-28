package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.ColorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("")
    public ResponseDTO<List<ColorDTO>> getAll() {
        return ResponseDTO.<List<ColorDTO>>builder()
                .data(colorService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<ColorDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                 @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ColorDTO>>>builder()
                .data(colorService.getByPageRequest(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<ColorDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<ColorDTO>builder()
                .status(200)
                .data(colorService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody @Valid ColorDTO colorDTO) {
        colorService.create(colorDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm màu sắc thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody ColorDTO colorDTO,
                                    @PathVariable Long id) {
        colorDTO.setId(id);
        colorService.update(colorDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa màu sắc thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá màu sắc thành công")
                .build();
    }
}
