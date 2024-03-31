package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.service.BillDetailService;
import com.duyhk.clothing_ecommerce.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/CTDH")
public class CTDHController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long billId) {
        model.addAttribute("bill", billService.getById(billId));
        model.addAttribute("billDetails", billDetailService.getAllByBillId(billId));
        return "KH/MyOrder/CTDH/CTDH";
    }
}
