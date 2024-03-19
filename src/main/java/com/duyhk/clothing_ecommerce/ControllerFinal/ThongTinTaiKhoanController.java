package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.entity.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/thong-tin-tai-khoan")
@Controller
public class ThongTinTaiKhoanController {

    @Autowired
    private HttpSession session;

    @GetMapping
    public String home(Model model){
        Users users = (Users) session.getAttribute("user");
        boolean isLogin = users == null ? false : true;
        model.addAttribute("isLogin", isLogin);
        if(!isLogin){
            return "redirect:/account/dang-nhap";
        }


        return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
    }

}
