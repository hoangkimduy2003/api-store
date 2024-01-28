package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchUserDTO;
import com.duyhk.clothing_ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public ResponseDTO<List<UserDTO>> getAll() {
        return ResponseDTO.<List<UserDTO>>builder()
                .data(customerService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<UserDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<UserDTO>>>builder()
                .data(customerService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<UserDTO>builder()
                .status(200)
                .data(customerService.getById(id))
                .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<UserDTO>>> search(@RequestBody SearchUserDTO searchUserDTO) {
        return ResponseDTO.<PageDTO<List<UserDTO>>>builder()
                .data(customerService.searchCustomer(searchUserDTO))
                .status(200)
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody UserDTO userDTO) {
        customerService.create(userDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm khách hàng thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody UserDTO userDTO,
                                    @PathVariable Long id) {
        userDTO.setId(id);
        customerService.update(userDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa khách hàng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá khách hàng thành công")
                .build();
    }
}
