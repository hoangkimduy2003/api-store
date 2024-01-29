package com.duyhk.clothing_ecommerce.ControllerFinal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {
    @GetMapping
    public String home(Model model){
        return "ThongKe/ThongKe";
    }
}
