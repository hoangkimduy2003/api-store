package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.KeyMap.KeyProduct;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDetailDTO;
import com.duyhk.clothing_ecommerce.service.ProductDetailService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/CTSP")
public class CTSPOnlineController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    public String home(@RequestParam("productDetailId") Long id,
                       Model model){
        model.addAttribute("product", productService.getById(id));
        List<ProductDetailDTO> listProductDetail = productDetailService.getByIdSp(new PageRequestDTO(0,100),id).getData();
        model.addAttribute("listDetail", productDetailService.getByIdSp(new PageRequestDTO(0,100),id));
        return "KH/ThongTinSP/ThongTinSP";
    }
}
