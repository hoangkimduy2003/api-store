package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {

    @Autowired
     private ThongKeService thongKeService;

    @GetMapping
    public String home(Model model){
        return "ThongKe/ThongKe";
    }

    @ResponseBody
    @GetMapping("/getSumFinal")
    public List<BillFinal> getSumFinal(){
        return thongKeService.getSumFinal();
    }
}
