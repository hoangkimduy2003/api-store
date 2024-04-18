package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.BillDTO;
import com.duyhk.clothing_ecommerce.dto.BillDetailDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.service.BillDetailService;
import com.duyhk.clothing_ecommerce.service.BillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/don-hang")
public class TatCaDonHangController {

    @Autowired
    private BillService billService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BillDetailService billDetailService;

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
            if (!"".equals(searchBillDTO.getStrDateStart()) && searchBillDTO.getStrDateStart() != null) {
                searchBillDTO.setDateStart(sdf.parse(searchBillDTO.getStrDateStart()));
            }
            if (!"".equals(searchBillDTO.getStrDateEnd()) && searchBillDTO.getStrDateEnd() != null) {
                searchBillDTO.setDateEnd(sdf.parse(searchBillDTO.getStrDateEnd()));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("bills", billService.searchAtStore(searchBillDTO));
        model.addAttribute("searchBillDTO", searchBillDTO);
        return "TatCaDonHang/TatCaDonHang";
    }

    @GetMapping("/chi-tiet/{id}")
    public String detail(Model model, @PathVariable("id") Long billId) {
        model.addAttribute("bill", billService.getById(billId));
        model.addAttribute("billDetails", billDetailService.getAllByBillId(billId));
        return "TatCaDonHang/ChiTietDonHang/ChiTietDonHang";
    }

    @PostMapping("/edit/{id}/{typeRedi}")
    public String update(@ModelAttribute BillDTO billDTO, @PathVariable("id") Long id, @PathVariable("typeRedi") Long typeRedi) {
        billDTO.setId(id);
        billService.updateAddress(billDTO);
        if(typeRedi == 1){
            return "redirect:/don-hang/chi-tiet/" + id;
        }
        return "redirect:/CTDH/" + id;
    }

    @GetMapping("/checkSize/{id}")
    @ResponseBody
    public boolean update(@PathVariable("id") Long id) {
        return !(billDetailService.getAllByBillId(id).size() == 1l);
    }

    @GetMapping("/updateStatus/{billId}/{status}/{action}")
    public String xacNhan(@PathVariable("billId") Long billId,
                          @PathVariable("status") int status,
                          @PathVariable("action") int action,
                          @RequestParam(name = "ship", required = false) Long quantity,
                          @RequestParam(name = "reason", required = false) String reason) {
        if(quantity == null && reason == null) {
            billService.updateStatusById(billId, status);
        }else if(quantity != null){
            billService.updateStatusById(billId, status, quantity);
        } else{
            billService.updateStatusById(billId, status, reason);
        }
        if(action == 1){
            return "redirect:/don-hang/chi-tiet/" + billId;
        } else if (action == 2) {
            return "redirect:/my-order?status=" + status;
        }
        return "redirect:/don-hang";
    }

    @GetMapping("/delete/{detailId}/{billId}")
    public String delete(@PathVariable("detailId") Long detailId,
                         @PathVariable("billId")Long billId){

        billDetailService.delete(detailId);
        return "redirect:/don-hang/chi-tiet/" + billId;
    }

    @GetMapping("/update/{detailId}/{billId}/{quantityNew}")
    public String update(@PathVariable("detailId") Long detailId,
                         @PathVariable("billId")Long billId,
                         @PathVariable("quantityNew") Long quantity){
        BillDetailDTO billDetailDTO = new BillDetailDTO();
        BillDTO bill = new BillDTO();
        bill.setId(billId);
        billDetailDTO.setBill(bill);
        billDetailDTO.setId(detailId);
        billDetailDTO.setQuantity(quantity);
        billDetailService.update(billDetailDTO);
        return "redirect:/don-hang/chi-tiet/" + billId;
    }
}
