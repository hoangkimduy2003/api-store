package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import com.duyhk.clothing_ecommerce.service.BillService;
import com.duyhk.clothing_ecommerce.service.ProductService;
import com.duyhk.clothing_ecommerce.service.UserService;
import org.jfree.data.time.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {
    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/bieu-do")
    public ResponseEntity<?> apiThongKe(@RequestParam(required = false, name = "page") Integer page, Model model) {
        page = page == null ? 0 : page;
        model.addAttribute("list", billService.getByPageRequest(new PageRequestDTO(page, 5)));



//        return ResponseEntity.ok(billService.getBillByYear(2024)) ;
//        return ResponseEntity.ok(productService.getByBestSeller(new PageRequestDTO(page,5))) ;

        return ResponseEntity.ok(billService.getMoney(2024));
    }

    @GetMapping
    public String home(@RequestParam(required = false, name = "page") Integer page, Model model,
                       @ModelAttribute ProductDetail productDetail) {
        page = page == null ? 0 : page;
        // hoa don
        model.addAttribute("list", billService.getCreaterBill(new PageRequestDTO(page, 5)));
        //san pham
        model.addAttribute("bestseller", productService.getByBestSeller(new PageRequestDTO(page, 5)));
        model.addAttribute("productExist",productService.totalProductExist());
        model.addAttribute("productsold",productService.totalProductSold());

        //tong hoa don
        long billCount = billService.SoLuongBill();
        model.addAttribute("billCount", billCount);
        // tong doanh thu
        model.addAttribute("revenue", billService.doanhThuBill());
        // tong so khach hang
        model.addAttribute("totalCustomers", userService.totalCustomers());
        // hoa don dang giao
        model.addAttribute("pendingVoice", billService.pendingInvoice());
        // hoa don da giao
        model.addAttribute("billTransacted", billService.billTransacted());
        // hoa don huy
        model.addAttribute("billCancelled", billService.billCancelled());
        // bieu do doanh thu
        model.addAttribute("month1",billService.revenueMonth(1,2024));
        model.addAttribute("month2",billService.revenueMonth(2,2024));
        model.addAttribute("month3",billService.revenueMonth(3,2024));
        model.addAttribute("month4",billService.revenueMonth(4,2024));
        model.addAttribute("month5",billService.revenueMonth(5,2024));
        model.addAttribute("month6",billService.revenueMonth(6,2024));
        model.addAttribute("month7",billService.revenueMonth(7,2024));
        model.addAttribute("month8",billService.revenueMonth(8,2024));
        model.addAttribute("month9",billService.revenueMonth(9,2024));
        model.addAttribute("month10",billService.revenueMonth(10,2024));
        model.addAttribute("month11",billService.revenueMonth(11,2024));
        model.addAttribute("month12",billService.revenueMonth(12,2024));


           model.addAttribute("month",billService.getMonth(2024));
           model.addAttribute("money",billService.getMoney(2024));


        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        //doanh thu
        model.addAttribute("revenueDay",billService.revenueDay(day,month,year)==null?"0":billService.revenueDay(day,month,year));
        model.addAttribute("revenueMonth",billService.revenueMonth(month,year)==null?"0":billService.revenueMonth(month,year));
        model.addAttribute("revenueYear",billService.revenueYear(year)==null?"0":billService.revenueYear(year));
        return "ThongKe/ThongKe";
    }


}
