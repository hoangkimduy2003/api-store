package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/CTDH")
public class CTDHController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long billId) {
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
        model.addAttribute("bill", billService.getById(billId));
        model.addAttribute("billDetails", billDetailService.getAllByBillId(billId));
        return "KH/MyOrder/CTDH/CTDH";
    }
}
