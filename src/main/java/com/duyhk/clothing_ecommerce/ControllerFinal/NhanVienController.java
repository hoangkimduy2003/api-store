package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchUserDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", userService.getByPageRequest(new PageRequestDTO(page, 5)));
        model.addAttribute("x","/nhan-vien?page=");
        return "NhanVien/NhanVien";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("name") String fullName,
            @RequestParam("status") Integer status,
            @RequestParam(required = false, name = "page") Integer page,
            Model model
    ) {
        page = page == null ? 0 : page;
        SearchUserDTO searchUserDTO = SearchUserDTO.builder()
                .fullName(fullName)
                .status(status)
                .page(page)
                .size(5)
                .build();
        PageDTO<List<UserDTO>> list =  userService.searchStaff(searchUserDTO);
        model.addAttribute("list", userService.searchStaff(searchUserDTO));
        model.addAttribute("x","/nhan-vien/search?name="+fullName+"&status="+status+"&page=");
        return "NhanVien/NhanVien";
    }

    @PostMapping("/action")
    public String action(@ModelAttribute UserDTO user) {
        if (user.getId() == null) {
            user.setRole(Role.STAFF);
            userService.create(user);
        } else
            userService.update(user);
        return "redirect:/nhan-vien";
    }
}
