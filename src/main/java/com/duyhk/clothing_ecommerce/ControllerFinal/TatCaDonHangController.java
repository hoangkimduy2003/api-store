package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.BillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/don-hang")
public class TatCaDonHangController {

    @Autowired
    private BillService billService;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String home(Model model, @ModelAttribute SearchBillDTO searchBillDTO) {
//        Users users = (Users) session.getAttribute("user");
//        boolean isLogin = users == null ? false : true;
//        model.addAttribute("isLogin", isLogin);
//        if(!isLogin){
//            return "redirect:/account/dang-nhap";
//        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(!"".equals(searchBillDTO.getStrDateStart()) && searchBillDTO.getStrDateStart() != null){
                searchBillDTO.setDateStart(sdf.parse(searchBillDTO.getStrDateStart()));
            }
            if(!"".equals(searchBillDTO.getStrDateEnd()) && searchBillDTO.getStrDateEnd() != null){
                searchBillDTO.setDateEnd(sdf.parse(searchBillDTO.getStrDateEnd()));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("bills", billService.searchAtStore(searchBillDTO));
        model.addAttribute("searchBillDTO", searchBillDTO);
        return "TatCaDonHang/TatCaDonHang";
    }
}
