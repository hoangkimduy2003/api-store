package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public ResponseDTO<List<AddressDTO>> getAll() {
        return ResponseDTO.<List<AddressDTO>>builder()
                .data(addressService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page/user")
    public ResponseDTO<PageDTO<List<AddressDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size,
                                                                   @RequestParam("id") Long id) {
        return ResponseDTO.<PageDTO<List<AddressDTO>>>builder()
                .data(addressService.getByUserId(id, new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<AddressDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<AddressDTO>>>builder()
                .data(addressService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }


    @GetMapping("/{id}")
    public ResponseDTO<AddressDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<AddressDTO>builder()
                .data(addressService.getById(id))
                .status(200)
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody AddressDTO addressDTO) {
        addressService.create(addressDTO);
        return ResponseDTO.<Void>builder()
                .msg("Thêm địa chỉ thành công")
                .status(200)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> updateById(@RequestBody AddressDTO addressDTO,
                                        @PathVariable Long id) {
        addressDTO.setId(id);
        addressService.update(addressDTO);
        return ResponseDTO.<Void>builder()
                .msg("Thêm địa chỉ thành công")
                .status(200)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseDTO.<Void>builder()
                .msg("Thêm địa chỉ thành công")
                .status(200)
                .build();
    }
}
