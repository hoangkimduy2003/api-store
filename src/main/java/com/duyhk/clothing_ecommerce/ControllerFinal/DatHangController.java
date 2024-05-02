package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.BillDTO;
import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.CartDetailDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.entity.Cart;
import com.duyhk.clothing_ecommerce.entity.CartDetail;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.reponsitory.CartDetailReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.CartReponsitory;
import com.duyhk.clothing_ecommerce.service.BillService;
import com.duyhk.clothing_ecommerce.service.CartDetailService;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private CartDetailReponsitory cartDetailRepo;

    @Autowired
    CartReponsitory cartRepo;

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
        billDTO.getUser().setId(users.getId());
        billService.createBillOnline(billDTO);
        return "redirect:/my-order";
    }

    @GetMapping("/checkCart")
    @ResponseBody
    public boolean checkCart(){
        Long cartId = (Long) session.getAttribute("cartId");
        CartDTO cartDTO = cartService.getById(cartId);
        return cartDTO.getTotalMoney() > 0;
    }

    @GetMapping("/checkOrder")
    @ResponseBody
    public boolean checkOrder(){
        Long cartId = (Long) session.getAttribute("cartId");
        Cart cart = cartRepo.findById(cartId).orElse(null);
        boolean isCheck = true;
        List<CartDetail> carts = cartDetailRepo.findByCartId(cartId);
        for(CartDetail x: carts){
            if(x.getProductDetail().getQuantity() == 0){
                cart.setTotalProduct(cart.getTotalProduct() - 1);
                cart.setTotalMoney(cart.getTotalMoney() - x.getQuantity() * x.getProductDetail().getPriceSale());
                cartDetailRepo.deleteById(x.getId());
                isCheck = false;
            }else{
                if((x.getQuantity() > x.getProductDetail().getQuantity())){
                    cart.setTotalMoney(cart.getTotalMoney() -
                            (x.getQuantity() * x.getProductDetail().getPrice()) +
                            (x.getProductDetail().getQuantity() * x.getProductDetail().getPriceSale()));
                    x.setQuantity(x.getProductDetail().getQuantity());
                    cartDetailRepo.save(x);
                    isCheck = false;
                }
            }
        }
        cartRepo.save(cart);
        return isCheck;
    }
}
