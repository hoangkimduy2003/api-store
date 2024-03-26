package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Cart;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.BillService;
import com.duyhk.clothing_ecommerce.service.CartDetailService;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BillService billService;

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

    @PostMapping("/order")
    public String order(@ModelAttribute BillDTO billDTO){
        Long cartId = (Long) session.getAttribute("cartId");
        Users users = (Users) session.getAttribute("user");
        if(cartId == null){
            return "redirect:/account/dang-nhap";
        }
        billDTO.setCartId(cartId);
        billDTO.setUser(new UserDTO(users.getId()));
        billService.createBillOnline(billDTO);
        return "redirect:/trang-chu";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute CartDetailDTO cartDetailDTO){
        Long cartId = (Long) session.getAttribute("cartId");
        Users users = (Users) session.getAttribute("user");
        if(cartId == null){
            return "redirect:/account/dang-nhap";
        }
        CartDTO cart = new CartDTO();
        cart.setId(cartId);
        cartDetailDTO.setCart(cart);
        cartDetailService.update(cartDetailDTO);
        return "redirect:/gio-hang";
    }
}
