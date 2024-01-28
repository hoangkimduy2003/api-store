package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/voucher")
@CrossOrigin
@RestController
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("")
    public ResponseDTO<List<VoucherDTO>> getAll() {
        return ResponseDTO.<List<VoucherDTO>>builder()
                .data(voucherService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<VoucherDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<VoucherDTO>>>builder()
                .data(voucherService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<VoucherDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<VoucherDTO>builder()
                .status(200)
                .data(voucherService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody VoucherDTO voucherDTO) {
        voucherService.create(voucherDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm voucher thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody VoucherDTO voucherDTO,
                                    @PathVariable Long id) {
        voucherDTO.setId(id);
        voucherService.update(voucherDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa voucher thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        voucherService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá voucher thành công")
                .build();
    }
}
