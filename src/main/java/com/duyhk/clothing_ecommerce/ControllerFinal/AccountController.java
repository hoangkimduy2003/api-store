package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @GetMapping("/dang-nhap")
    public String home(Model model) {
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("isLogin", users == null ? false : true);
        return "DangNhap/DangNhap";
    }

    @GetMapping("/createAccount")
    public String viewCreate(Model model) {
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("isLogin", users == null ? false : true);
        return "DangKy/DangKy";
    }

    @GetMapping("/changePassword")
    public String viewChangePassword(Model model) {
        Users users = (Users) session.getAttribute("user");
        boolean isLogin = users == null ? false : true;
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        model.addAttribute("isLogin", users == null ? false : true);
        if (!isLogin) {

            return "redirect:/account/dang-nhap";
        } else {
            model.addAttribute("user", users);
            return "KH/DoiMatKhau/DoiMatKhau";
        }

    }

    @GetMapping("/dang-nhap/out")
    public String logout() {
        session.setAttribute("user", null);
        return "redirect:/account/dang-nhap";
    }


    @PostMapping("/dang-nhap")
    public String login(@ModelAttribute LoginDTO loginDTO, Model model) {
        Users user = userService.login(loginDTO.getPhoneNumber(), loginDTO.getPassword());
        if (user == null) {
            model.addAttribute("msg", "Số điện thoại hoặc mật khẩu không đúng");
            return "DangNhap/DangNhap";
        }
        session.setAttribute("user", user);
        if(user != null){
            CartDTO cart = cartService.getByUserId(user.getId());
            Long size = cart.getTotalProduct();
            session.setAttribute("cartId",cart.getId());
            model.addAttribute("sizeCart",size);
            model.addAttribute("isAdmin",(user.getRole() == Role.ADMIN) ? true : false);
        }
        if(user.getRole() == Role.ADMIN){
            return "redirect:/thong-ke";
        }
        return "redirect:/trang-chu";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute ChangePasswordDTO changePasswordDTO, Model model) {
        Users users = (Users) session.getAttribute("user");
        Users user = userService.login(users.getPhoneNumber(), changePasswordDTO.getOldPassword());
        model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        if (changePasswordDTO.getPasswordConfirm().equals("")
                ||changePasswordDTO.getNewPassword().equals("")
                ||!(changePasswordDTO.getNewPassword().equals(changePasswordDTO.getPasswordConfirm()))) {
            session.setAttribute("user", users);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
            model.addAttribute("isLogin", users == null ? false : true);
            return "redirect:/account/changePassword";
        } else if (user == null) {
            session.setAttribute("user", users);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
            model.addAttribute("isLogin", users == null ? false : true);
            model.addAttribute("tb", "Mật khẩu không chính xác");
            return "KH/DoiMatKhau/DoiMatKhau";
        } else {
            users.setPassword(changePasswordDTO.getNewPassword());
            userService.update(userService.convertToDto(users));
            model.addAttribute("tb","Đổi mật khẩu thành công. Vui lòng đăng nhập lại");
            session.setAttribute("user", null);
            return "DangNhap/DangNhap";
        }
//        return "redirect:/account/changePassword";
    }

    @PostMapping("/createAccount")
    public String signUp(@ModelAttribute RegisterDTO registerDTO, Model model) {
        Users user = userService.findByPhoneNumber(registerDTO.getPhoneNumber());
        if (user != null) {
            model.addAttribute("msg", "Số điện thoại đã được đăng ký");
            return "DangKy/DangKy";
        }
        if (registerDTO.getPhoneNumber() == null || registerDTO.getFullName() == null
                || registerDTO.getPassword().equals("")
                || !(registerDTO.getPasswordConfirm().equals(registerDTO.getPassword()))) {
            return "redirect:/account/createAccount";
        } else {
            userService.create(userService.convertToUserDto(registerDTO));
            return "DangNhap/DangNhap";
        }
    }
}
