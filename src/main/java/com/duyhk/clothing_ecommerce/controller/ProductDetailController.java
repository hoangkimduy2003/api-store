package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/productDetail")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("")
    public ResponseDTO<List<ProductDetailDTO>> getAll() {
        return ResponseDTO.<List<ProductDetailDTO>>builder()
                .data(productDetailService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<ProductDetailDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                         @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ProductDetailDTO>>>builder()
                .data(productDetailService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }
    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<ProductDetailDTO>>> search(@RequestBody SearchProductDTO searchProductDTO) {
        return ResponseDTO.<PageDTO<List<ProductDetailDTO>>>builder()
                .data(productDetailService.search(searchProductDTO))
                .status(200)
                .build();
    }
    @GetMapping("/{id}")
    public ResponseDTO<ProductDetailDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<ProductDetailDTO>builder()
                .status(200)
                .data(productDetailService.getById(id))
                .build();
    }

    @GetMapping("/code/{code}")
    public ResponseDTO<ProductDetailDTO> findProductDetailByProductDetailCode(@PathVariable("code") String code) {
        return ResponseDTO.<ProductDetailDTO>builder()
                .status(200)
                .data(productDetailService.findProductDetailByProductDetailCode(code))
                .build();
    }


    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody ProductDetailDTO productDetailDTO) {
        productDetailService.create(productDetailDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm chi tiết sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody ProductDetailDTO productDetailDTO,
                                    @PathVariable Long id) {
        productDetailDTO.setId(id);
        productDetailService.update(productDetailDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa chi tiết sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        productDetailService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá chi tiết sản phẩm thành công")
                .build();
    }
}
