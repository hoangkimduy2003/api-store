package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", userService.getByPageRequest(new PageRequestDTO(page, 5)));
        return "NhanVien/NhanVien";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute UserDTO user) {
        if (user.getId() == null)
            userService.create(user);
        else
            userService.update(user);
        return "redirect:/nhan-vien";
    }
}
