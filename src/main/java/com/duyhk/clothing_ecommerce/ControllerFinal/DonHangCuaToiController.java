package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillCustomerDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.BillDetailService;
import com.duyhk.clothing_ecommerce.service.BillService;
import com.duyhk.clothing_ecommerce.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/my-order")
@Controller
public class DonHangCuaToiController {

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @GetMapping
    public String home(Model model, @ModelAttribute SearchBillCustomerDTO searchBillCustomerDTO){
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
        model.addAttribute("status",searchBillCustomerDTO.getStatus());
        searchBillCustomerDTO.setUserId(users.getId());
        model.addAttribute("bills", billService.searchByCustomer(searchBillCustomerDTO));
        return "KH/MyOrder/MyOrder";
    }
}
