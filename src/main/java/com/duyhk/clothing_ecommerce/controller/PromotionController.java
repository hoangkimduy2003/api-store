package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
@CrossOrigin
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("")
    public ResponseDTO<List<PromotionDTO>> getAll() {
        return ResponseDTO.<List<PromotionDTO>>builder()
                .data(promotionService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<PromotionDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                     @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<PromotionDTO>>>builder()
                .data(promotionService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<PromotionDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<PromotionDTO>builder()
                .status(200)
                .data(promotionService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody PromotionDTO promotionDTO) {
        promotionService.create(promotionDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm khuyến mại thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody PromotionDTO promotionDTO,
                                    @PathVariable Long id) {
        promotionDTO.setId(id);
        promotionService.update(promotionDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa khuyến mại thành công")
                .build();
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseDTO<Void> changeStatus(@RequestBody PromotionDTO promotionDTO,
                                            @PathVariable Long id) {
        promotionDTO.setId(id);
        promotionService.changeStatus(promotionDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg(promotionDTO.getStatus() == 1 ? "Khuyến mại đã được đổi sang trạng thái hoạt động" : "Khuyến mại đã được đổi sang trạng thái không hoạt động")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá khuyến mại thành công")
                .build();
    }
}
