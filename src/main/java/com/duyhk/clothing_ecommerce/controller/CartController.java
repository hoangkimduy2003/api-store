package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public ResponseDTO<List<CartDTO>> getAll() {
        return ResponseDTO.<List<CartDTO>>builder()
                .data(cartService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<CartDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<CartDTO>>>builder()
                .data(cartService.getByPageRequest(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<CartDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<CartDTO>builder()
                .status(200)
                .data(cartService.getById(id))
                .build();
    }
    @GetMapping("/user/{id}")
    public ResponseDTO<CartDTO> getByUserId(@PathVariable Long id) {
        return ResponseDTO.<CartDTO>builder()
                .status(200)
                .data(cartService.getByUserId(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody CartDTO cartDTO) {
        cartService.create(cartDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Tạo giỏ hàng thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody CartDTO cartDTO,
                                    @PathVariable Long id) {
        cartDTO.setId(id);
        cartService.update(cartDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa giỏ hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        cartService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá giỏ hàng thành công")
                .build();
    }
}
