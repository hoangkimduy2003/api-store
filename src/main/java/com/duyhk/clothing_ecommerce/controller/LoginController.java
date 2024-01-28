package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.config.JwtService;
import com.duyhk.clothing_ecommerce.dto.LoginDTO;
import com.duyhk.clothing_ecommerce.dto.ResponseDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseDTO<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getPhoneNumber(),loginDTO.getPassword())
        );
        Users user = userService.findByPhoneNumber(loginDTO.getPhoneNumber());
        return ResponseDTO.<String>builder()
                .status(200)
                .msg("Đăng nhập thành công")
                .data(jwtService.generateToken(user))
                .build();
    }
    @PostMapping("/register")
    public ResponseDTO<Void> register(@RequestBody @Valid UserDTO userDTO) {
        userService.create(userDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Đăng kí thành công")
                .build();
    }

    @PutMapping("/update/{id}")
    public ResponseDTO<Void> changePassword(@RequestBody @Valid UserDTO userDTO,
                                            @PathVariable Long id){
        userDTO.setId(id);
        userService.update(userDTO);
        return ResponseDTO.<Void>builder()
                .msg("Thay đổi mật khẩu thành công")
                .status(200)
                .build();
    }
}
