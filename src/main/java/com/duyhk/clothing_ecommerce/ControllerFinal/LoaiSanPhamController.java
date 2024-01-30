package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CategoryDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.SizeDTO;
import com.duyhk.clothing_ecommerce.service.CategoryService;
import com.duyhk.clothing_ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loai-san-pham")
public class LoaiSanPhamController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", categoryService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "LoaiSanPham/LoaiSanPham";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null)
            categoryService.create(categoryDTO);
        else
            categoryService.update(categoryDTO);
        return "redirect:/loai-san-pham";
    }
}
