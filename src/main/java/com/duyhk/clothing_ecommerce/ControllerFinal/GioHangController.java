package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.CartDetailService;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String home(Model model) {
        Users users = (Users) session.getAttribute("user");
        boolean isLogin = users == null ? false : true;
        model.addAttribute("isLogin", isLogin);
        if(!isLogin){
            return "redirect:/account/dang-nhap";
        }
        if(users != null){
            CartDTO cart = cartService.getByUserId(users.getId());
            model.addAttribute("sizeCart",cart.getTotalProduct());
                model.addAttribute("cart",cart);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN && users != null) ? true : false);
        }
        model.addAttribute("listBestSell", productService.getByBestSeller(new PageRequestDTO()));
        model.addAttribute("listNew",productService.getByNew(new PageRequestDTO()));
        return "KH/GioHang/GioHang";
    }

    @GetMapping("/delete/{cartDetailId}")
    public String deleteById(@PathVariable("cartDetailId") Long cartDetailId){
        cartDetailService.delete(cartDetailId);
        return "redirect:/gio-hang";
    }
}
