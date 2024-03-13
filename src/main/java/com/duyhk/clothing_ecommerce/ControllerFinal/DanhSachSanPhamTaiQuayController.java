package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.BillDetailDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/danh-sach-san-pham")
public class DanhSachSanPhamTaiQuayController {
    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/{idBill}")
    public String home(Model model, @PathVariable("idBill") long idBill, @ModelAttribute SearchProductDTO searchProductDTO){
        model.addAttribute("idBill",idBill);
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("sizes",sizeService.getAll());
        model.addAttribute("colors",colorService.getAll());
        model.addAttribute("list",productDetailService.search(searchProductDTO));
        model.addAttribute("searchProductDTO", searchProductDTO);
        return "GioHangTaiQuay/GioHangTaiQuay";
    }

    @PostMapping("/create")
    public String create( @ModelAttribute BillDetailDTO billDetailDTO){
        billDetailService.create(billDetailDTO);
        return "redirect:/tai-quay?idBill="+billDetailDTO.getBill().getId();
    }

    @PostMapping("/update")
    public String update( @ModelAttribute BillDetailDTO billDetailDTO){
        billDetailService.update(billDetailDTO);
        return "redirect:/tai-quay?idBill="+billDetailDTO.getBill().getId();
    }
}
