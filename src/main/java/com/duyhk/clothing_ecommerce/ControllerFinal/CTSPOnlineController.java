package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.CartDTO;
import com.duyhk.clothing_ecommerce.dto.CartDetailDTO;
import com.duyhk.clothing_ecommerce.dto.KeyMap.KeyProduct;
import com.duyhk.clothing_ecommerce.dto.ObjectValidate.ProductDetailQuantity;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDetailDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.CartDetailService;
import com.duyhk.clothing_ecommerce.service.CartService;
import com.duyhk.clothing_ecommerce.service.ProductDetailService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/CTSP")
public class CTSPOnlineController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private HttpSession session;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String home(@RequestParam("productDetailId") Long id,
                       Model model){
        Users users = (Users) session.getAttribute("user");
        model.addAttribute("isLogin", users == null ? false : true);
        if(users != null){
            CartDTO cart = cartService.getByUserId(users.getId());
            Long size = cart.getTotalProduct();
            session.setAttribute("cartId",cart.getId());
            model.addAttribute("sizeCart",size);
            model.addAttribute("isAdmin",(users.getRole() == Role.ADMIN) ? true : false);
        }
        model.addAttribute("product", productService.getById(id));
        List<ProductDetailDTO> listProductDetail = productDetailService.getByIdSp(new PageRequestDTO(0,100),id).getData();
        Set<String> colors = new HashSet<>();
         listProductDetail.stream().forEach(
             x -> {
                colors.add(x.getColor().getName());
             }
         );
         model.addAttribute("colors", colors);
        model.addAttribute("listDetail", productDetailService.getByIdSp(new PageRequestDTO(0,100),id));
        return "KH/ThongTinSP/ThongTinSP";
    }

    @PostMapping("/create/{idProduct}")
    public String createCartDetail(@PathVariable("idProduct")Long idProduct,
                                   @ModelAttribute CartDetailDTO cartDetailDTO){
        Long cartId = (Long) session.getAttribute("cartId");
        if(cartId == null){
            return "redirect:/account/dang-nhap";
        }
        cartDetailDTO.setCart(new CartDTO(cartId));
        cartDetailService.create(cartDetailDTO);
        return "redirect:/CTSP?productDetailId="+idProduct;
    }

    @GetMapping("/getByColor/{color}/{idProduct}")
    @ResponseBody
        public Set<ProductDetailQuantity> getSizesByColor(@PathVariable("color") String colorName,
                                                      @PathVariable("idProduct") Long idProduct){
        Set<ProductDetailQuantity> setSizes = new HashSet<>();
        List<ProductDetailDTO> list = productDetailService.searchByColorName(colorName,idProduct);
        list.forEach(x -> {
            setSizes.add(new ProductDetailQuantity(x.getId(), x.getSize().getName(), x.getQuantity()));
        });
        return setSizes;
    }

    @GetMapping("/checkQuantity/{productDetailId}")
    @ResponseBody
    public Long isCheck(@PathVariable("productDetailId")Long productDetailId){
        Users users = (Users) session.getAttribute("user");
        if(users != null){
            CartDTO cart = cartService.getByUserId(users.getId());
            return cartDetailService.findByCartIdAndProductDetailId(cart.getId(),productDetailId).getQuantity();
        }
        return -1l;
    }
}
