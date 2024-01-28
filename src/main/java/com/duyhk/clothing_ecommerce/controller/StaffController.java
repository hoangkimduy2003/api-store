package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchUserDTO;
import com.duyhk.clothing_ecommerce.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/staff")
@RestController
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping("")
    public ResponseDTO<List<UserDTO>> getAll() {
        return ResponseDTO.<List<UserDTO>>builder()
                .data(staffService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<UserDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<UserDTO>>>builder()
                .data(staffService.getByPageRequest(new PageRequestDTO(page,size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<UserDTO>builder()
                .status(200)
                .data(staffService.getById(id))
                .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<UserDTO>>> search(@RequestBody SearchUserDTO searchUserDTO) {
        return ResponseDTO.<PageDTO<List<UserDTO>>>builder()
                .data(staffService.searchStaff(searchUserDTO))
                .status(200)
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody UserDTO userDTO) {
        staffService.create(userDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm nhân viên thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@RequestBody UserDTO userDTO,
                                    @PathVariable Long id) {
        userDTO.setId(id);
        staffService.update(userDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa nhân viên thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        staffService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá nhân viên thành công")
                .build();
    }
}
