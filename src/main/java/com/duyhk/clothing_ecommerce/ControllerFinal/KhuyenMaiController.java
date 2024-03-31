package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/khuyen-mai")
public class KhuyenMaiController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public String home(Model model, @RequestParam(required = false, name = "page") Integer page){
        model.addAttribute("list", voucherService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "KhuyenMai/KhuyenMai";
    }
}
