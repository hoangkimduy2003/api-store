package com.duyhk.clothing_ecommerce.ControllerFinal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thanh-toan")
public class ThanhToanTaiQuayController {
    @GetMapping
    public String home(){

        return "ThanhToan/ThanhToan";
    }
}
