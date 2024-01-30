package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.ColorDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.SizeDTO;
import com.duyhk.clothing_ecommerce.service.ColorService;
import com.duyhk.clothing_ecommerce.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kich-co")
public class KichCoController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", sizeService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "KichCo/KichCo";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute SizeDTO size) {
        if (size.getId() == null)
            sizeService.create(size);
        else
            sizeService.update(size);
        return "redirect:/kich-co";
    }
}
