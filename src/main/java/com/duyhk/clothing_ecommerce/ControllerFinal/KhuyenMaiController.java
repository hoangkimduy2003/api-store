package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.VoucherDTO;
import com.duyhk.clothing_ecommerce.entity.Voucher;
import com.duyhk.clothing_ecommerce.service.UserService;
import com.duyhk.clothing_ecommerce.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khuyen-mai")
public class KhuyenMaiController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model, @RequestParam(required = false, name = "page") Integer page){
        model.addAttribute("list", voucherService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "KhuyenMai/KhuyenMai";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute VoucherDTO voucherDTO){
        voucherService.action(voucherDTO);
        return "redirect:/khuyen-mai";
    }

    @GetMapping("/sendEmail/{id}")
    @ResponseBody
    public void sendEmail(@PathVariable("id") Long id){
        voucherService.sendEmail(id);
    }


    @GetMapping("/voucherApp/{voucherCode}")
    @ResponseBody
    public VoucherDTO findByVoucherCode(@PathVariable("voucherCode") String voucherCode){
        return voucherService.findByVoucherCode(voucherCode);
    }
}
