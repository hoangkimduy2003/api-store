package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.BillDTO;
import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dat-hang")
public class DatHangController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BillService billService;

    @Autowired
    private CartDetailService cartDetailService;

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
        if(users != null){
            CartDTO cart = cartService.getByUserId(users.getId());
            model.addAttribute("sizeCart",cart.getTotalProduct());
            model.addAttribute("cart",cart);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN && users != null) ? true : false);
        }
        return "KH/DatHang/DatHang";
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
}
