package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.LoginDTO;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account/dang-nhap")
public class DangNhapController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String home(Model model){
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("isLogin", users == null ? false : true);
        return "DangNhap/DangNhap";
    }

    @GetMapping("/out")
    public String logout(){
        session.setAttribute("user", null);
        return "redirect:/account/dang-nhap";
    }

    @PostMapping
    public String login(@ModelAttribute LoginDTO loginDTO, Model model){
        Users user = userService.login(loginDTO.getPhoneNumber(),loginDTO.getPassword());
        if(user == null){
            model.addAttribute("msg", "Số điện thoại hoặc mật khẩu không đúng");
            return "DangNhap/DangNhap";
        }
        session.setAttribute("user",user);
        return "redirect:/trang-chu";
    }
}
