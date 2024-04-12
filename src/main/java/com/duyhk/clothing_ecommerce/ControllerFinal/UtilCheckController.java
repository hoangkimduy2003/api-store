package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class UtilCheckController {

    @Autowired
    private BillService billService;

    @GetMapping("/statusBill/{id}/{status}")
    public boolean checkStatusBill(@PathVariable("id") Long id,
                                   @PathVariable("status") Integer status){
        return billService.checkStatusBill(id,status);
    }
}
