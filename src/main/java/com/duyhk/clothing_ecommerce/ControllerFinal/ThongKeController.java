package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.dto.thongKe.BillRate;
import com.duyhk.clothing_ecommerce.dto.thongKe.SearchThongKeDTO;
import com.duyhk.clothing_ecommerce.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thong-ke")
public class ThongKeController {

    @Autowired
     private ThongKeService thongKeService;

    @GetMapping
    public String home(Model model, @ModelAttribute SearchThongKeDTO searchThongKeDTO){
        model.addAttribute("rate" , thongKeService.getRate(searchThongKeDTO));
        return "ThongKe/ThongKe";
    }

    @GetMapping("/doanh-thu")
    public String doanhThu(Model model, @ModelAttribute SearchThongKeDTO searchThongKeDTO){
        model.addAttribute("rate" , thongKeService.getRate(searchThongKeDTO));
        List<BillFinal> list = thongKeService.getSumFinal(searchThongKeDTO);
        List<String> lable = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (BillFinal x: list){
            lable.add(x.getDate());
            value.add(x.getTotalMoney()+ "");
        }
        model.addAttribute("lable", lable);
        model.addAttribute("values", value);
        model.addAttribute("searchThongKeDTO", searchThongKeDTO);
        return "ThongKe/DoanhThu/DoanhThu";
    }

    @GetMapping("/don-hang")
    public String bill(Model model, @ModelAttribute SearchThongKeDTO searchThongKeDTO){
        BillRate billRate = thongKeService.getBills(searchThongKeDTO);
        String date = searchThongKeDTO.getDate();
        List<BillFinal> listOnline = billRate.getSumBillOnline();
        List<String> lableOnline = new ArrayList<>();
        List<String> valueOnline = new ArrayList<>();
        for (BillFinal x: listOnline){
            lableOnline.add(x.getDate());
            valueOnline.add(x.getTotalMoney()+ "");
        }
        model.addAttribute("lableOnline", lableOnline);
        model.addAttribute("valueOnline", valueOnline);

        List<BillFinal> listStore = billRate.getSumBillStore();
        List<String> lableStore = new ArrayList<>();
        List<String> valueStore = new ArrayList<>();
        for (BillFinal x: listStore){
            lableStore.add(x.getDate());
            valueStore.add(x.getTotalMoney()+ "");
        }
        model.addAttribute("lableStore", lableStore);
        model.addAttribute("valueStore", valueStore);

        model.addAttribute("rate" , billRate);
        searchThongKeDTO.setDate(date);
        model.addAttribute("searchThongKeDTO",searchThongKeDTO);
        return "ThongKe/DonHang/DonHang";
    }

    @GetMapping("/san-pham")
    public String product(Model model, @ModelAttribute SearchThongKeDTO searchThongKeDTO){
        model.addAttribute("rate" , thongKeService.getProducts(searchThongKeDTO));
        model.addAttribute("searchThongKeDTO",searchThongKeDTO);
        return "ThongKe/SanPham/SanPham";
    }
//    @ResponseBody
//    @GetMapping("/getSumFinal")
//    public List<BillFinal> getSumFinal(){
//        return thongKeService.getSumFinal();
//    }
}
