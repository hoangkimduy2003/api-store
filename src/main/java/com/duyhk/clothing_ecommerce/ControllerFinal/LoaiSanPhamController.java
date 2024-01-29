package com.duyhk.clothing_ecommerce.ControllerFinal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loai-san-pham")
public class LoaiSanPhamController {
    @GetMapping
    public String home(){
        return "LoaiSanPham/LoaiSanPham";
    }
}
