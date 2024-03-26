package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/thong-tin-tai-khoan")
@Controller
public class ThongTinTaiKhoanController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @GetMapping
    public String home(Model model) {
        Users users = (Users) session.getAttribute("user");
        boolean isLogin = users == null ? false : true;
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        if (!isLogin) {
            return "redirect:/account/dang-nhap";
        } else {
            model.addAttribute("user", users);
            return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
        }
    }

    @PostMapping
    public String update(
            Model model,
            @ModelAttribute UserDTO userDTO
    ){
        Users user =null;
        Users users = (Users) session.getAttribute("user");
        boolean isLogin = users == null ? false : true;
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        userDTO.setId(users.getId());
        userDTO.setPassword(users.getPassword());
        if(!(userDTO.getPhoneNumber().equals(users.getPhoneNumber()))){
            user = userService.findByPhoneNumber(userDTO.getPhoneNumber());
        }
        if (user != null) {
            model.addAttribute("msg", "Số điện thoại đã được đăng ký");
            return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
        }
        if (userDTO.getPhoneNumber().equals("") || userDTO.getFullName().equals("")) {
            return "redirect:/thong-tin-tai-khoan";
        }else {
            // Kiểm tra số điện thoại hợp lệ
            String phoneNumberPattern = "^(0|\\+84)(3[2-9]|5[2689]|7[0|6-9]|8[1-9]|9[0-9])\\d{7}$";
            if (!userDTO.getPhoneNumber().matches(phoneNumberPattern)) {
                model.addAttribute("msg", "Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
            }
        }
        if(!userDTO.getEmail().equals("")){
            //Kiểm tra email
            String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if(!userDTO.getEmail().matches(emailPattern)){
                model.addAttribute("msgEmail", "Email không hợp lệ. Vui lòng nhập lại.");
                return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
            }
        }
        if(users.getId()!=null){
            userService.update(userDTO);
            UserDTO users2 =  userService.getById(userDTO.getId());
            Users users1 = userService.convertToEntity(users2);
            session.setAttribute("user", users1);
            model.addAttribute("user", userService.getById(userDTO.getId()));
            model.addAttribute("tb", "Cập nhật thông tin thành công");
            return "KH/ThongTinTaiKhoan/ThongTinTaiKhoan";
        }
        return "redirect:/thong-tin-tai-khoan";
    }
}
