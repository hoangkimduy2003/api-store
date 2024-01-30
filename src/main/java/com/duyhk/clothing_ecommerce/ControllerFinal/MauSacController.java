package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.ColorDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mau-sac")
public class MauSacController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", colorService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "MauSac/MauSac";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute ColorDTO color) {
        if (color.getId() == null)
            colorService.create(color);
        else
            colorService.update(color);
        return "redirect:/mau-sac";
    }
}
