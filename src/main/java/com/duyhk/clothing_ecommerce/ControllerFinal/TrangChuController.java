package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trang-chu")
public class TrangChuController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String home(Model model){
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("isLogin", users == null ? false : true);
        if(users != null){
            CartDTO cart = cartService.getByUserId(users.getId());
            Long size = cart.getTotalProduct();
            session.setAttribute("cartId",cart.getId());
            model.addAttribute("sizeCart",size);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        }
        model.addAttribute("listBestSell", productService.getByBestSeller(new PageRequestDTO()));
        model.addAttribute("listNew",productService.getByNew(new PageRequestDTO()));
        return "KH/TrangChu/TrangChu";
    }
}
