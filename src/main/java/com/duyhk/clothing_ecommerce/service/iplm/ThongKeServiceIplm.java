package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.reponsitory.ThongKeRepo;
import com.duyhk.clothing_ecommerce.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThongKeServiceIplm implements ThongKeService {

    @Autowired
    private ThongKeRepo thongKeRepo;

    @Override
    public List<BillFinal> getSumFinal() {
        List<Object[]> billFinal = thongKeRepo.getSumFinal();
        List<BillFinal> billFinals = new ArrayList<>();
        for( Object[] object : billFinal){

            BillFinal bill = new BillFinal();
            bill.setDate((Date) object[0]);
            bill.setTotalMoney(Math.round(Double.parseDouble(object[1]+ "")));
            billFinals.add(bill);
        }
        return billFinals;
    }
}
