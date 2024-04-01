package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchUserDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model, @ModelAttribute SearchUserDTO searchUserDTO){
        model.addAttribute("list",userService.searchStaff(searchUserDTO));
        model.addAttribute("searchUserDTO",searchUserDTO);
        return "NhanVien/NhanVien";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute UserDTO userDTO){
        userDTO.setRole(Role.ADMIN);
        if(userDTO.getId() == null){
            userService.create(userDTO);
        }else{
            userService.update(userDTO);
        }
        return "redirect:/nhan-vien";
    }
}
