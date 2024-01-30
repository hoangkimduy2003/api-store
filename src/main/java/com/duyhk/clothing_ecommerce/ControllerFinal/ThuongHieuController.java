package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.BrandDTO;
import com.duyhk.clothing_ecommerce.dto.CategoryDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.service.BrandService;
import com.duyhk.clothing_ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thuong-hieu")
public class ThuongHieuController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", brandService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "ThuongHieu/ThuongHieu";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute BrandDTO baBrandDTO) {
        if (baBrandDTO.getId() == null)
            brandService.create(baBrandDTO);
        else
            brandService.update(baBrandDTO);
        return "redirect:/thuong-hieu";
    }
}
