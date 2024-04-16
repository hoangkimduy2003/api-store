package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.thongKe.BillFinal;
import com.duyhk.clothing_ecommerce.dto.thongKe.BillRate;
import com.duyhk.clothing_ecommerce.dto.thongKe.SearchThongKeDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.reponsitory.ThongKeRepo;
import com.duyhk.clothing_ecommerce.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThongKeServiceIplm implements ThongKeService {

    @Autowired
    private ThongKeRepo thongKeRepo;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<BillFinal> getSumFinal(SearchThongKeDTO searchThongKeDTO) {
        List<Object[]> billFinal = null;
        try {
            searchThongKeDTO.setDate((searchThongKeDTO.getDate() == null || "".equals(searchThongKeDTO.getDate())) ? sdf.format(new Date()) : sdf.format(sdf.parse(searchThongKeDTO.getDate())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String[] _time = searchThongKeDTO.getDate().split("-");
        if (searchThongKeDTO.getShowType() == null) {
            billFinal = thongKeRepo.getSumDay(Long.parseLong(_time[0]));
        } else {
            if (searchThongKeDTO.getShowType() == 1) {
                billFinal = thongKeRepo.getSumDay(Long.parseLong(_time[0]));
            } else if (searchThongKeDTO.getShowType() == 2) {
                billFinal = thongKeRepo.getSumMonth(Long.parseLong(_time[0]));
            } else {
                billFinal = thongKeRepo.getSumYear();
            }
        }
        List<BillFinal> billFinals = new ArrayList<>();
        for (Object[] object : billFinal) {
            BillFinal bill = new BillFinal();
            bill.setDate((object[0]) + "");
            bill.setTotalMoney(Math.round(Double.parseDouble(object[1] + "")));
            billFinals.add(bill);
        }
        return billFinals;
    }

    @Override
    public BillRate getRate(SearchThongKeDTO searchThongKeDTO) {
        try {
            searchThongKeDTO.setDate((searchThongKeDTO.getDate() == null || "".equals(searchThongKeDTO.getDate())) ? sdf.format(new Date()) : sdf.format(sdf.parse(searchThongKeDTO.getDate())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        BillRate billRate = new BillRate();
        long totalBillsSuccess = thongKeRepo.findByBillTypeAndStatus(2, 5);
        long totalBillsCancel = thongKeRepo.findByBillTypeAndStatus(2, 0);
        long totalBillOnline = thongKeRepo.totalBillOnline(2);
        long totalUser = thongKeRepo.totalUser();
        long totalUserBuy = thongKeRepo.totalUserBy();
        Long totalProductSold = thongKeRepo.totalProductSold();
        totalProductSold = totalProductSold == null ? 0 : totalProductSold;
        long totalBill = thongKeRepo.totalBill(null,null,null,null);
        Long totalMoneyBill = thongKeRepo.totalMoneyBill();
        totalMoneyBill = totalMoneyBill == null ? 0 : totalMoneyBill;
        List<Bill> billNews = thongKeRepo.getBillNew(PageRequest.of(0, 5)).getContent();
        List<Product> products = thongKeRepo.getProductSale(PageRequest.of(0, 5)).getContent();
        String[] _time = searchThongKeDTO.getDate().split("-");
        Long totalMoneyDay = thongKeRepo.totalMoneyDay(Long.parseLong(_time[2]), Long.parseLong(_time[1]), Long.parseLong(_time[0]));
        Long totalMoneyMonth = thongKeRepo.totalMoneyMonth(Long.parseLong(_time[1]), Long.parseLong(_time[0]));
        Long totalMoneyYear = thongKeRepo.totalMoneyYear(Long.parseLong(_time[0]));
        totalMoneyDay = totalMoneyDay == null ? 0l : totalMoneyDay;
        totalMoneyMonth = totalMoneyMonth == null ? 0l : totalMoneyMonth;
        totalMoneyYear = totalMoneyYear == null ? 0l : totalMoneyYear;
        Long totalBillDay = thongKeRepo.totalBillDay(Long.parseLong(_time[2]), Long.parseLong(_time[1]), Long.parseLong(_time[0]));
        Long totalBillMonth = thongKeRepo.totalBillMonth(Long.parseLong(_time[1]), Long.parseLong(_time[0]));
        Long totalBillYear = thongKeRepo.totalBillYear(Long.parseLong(_time[0]));
        totalBillDay = totalBillDay == null ? 0l : totalBillDay;
        totalBillMonth = totalBillMonth == null ? 0l : totalBillMonth;
        totalBillYear = totalBillYear == null ? 0l : totalBillYear;
        try {
            Page<Bill> billInDay = thongKeRepo.billInToDay(PageRequest.of(searchThongKeDTO.getPage() == null ? 0 : searchThongKeDTO.getPage(), 5),
                    sdf.parse(searchThongKeDTO.getDate()));
            billRate.setBillToday(billInDay);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        billRate.setTotalBillYear(totalBillYear);
        billRate.setTotalBillMonth(totalBillMonth);
        billRate.setTotalBillDay(totalBillDay);
        billRate.setTotalMoneyDay(totalMoneyDay);
        billRate.setTotalMoneyMonth(totalMoneyMonth);
        billRate.setTotalMoneyYear(totalMoneyYear);
        billRate.setProductSale(products);
        billRate.setBillNews(billNews);
        billRate.setTotalUser(totalUser);
        billRate.setTotalBill(totalBill);
        billRate.setTotalProductSold(totalProductSold);
        billRate.setTotalMoneyBill(totalMoneyBill);
        billRate.setCompletionRate(Double.isNaN(((double) totalBillsSuccess / (double) totalBillOnline) * 100) ? 100 : ((double) totalBillsSuccess / (double) totalBillOnline) * 100);
        billRate.setCancelRate(Double.isNaN(((double) totalBillsCancel / (double) totalBillOnline) * 100) ? 0 : (((double) totalBillsCancel / (double) totalBillOnline) * 100));
        billRate.setBuyRate(Double.isNaN(((double) totalUserBuy / (double) totalUser) * 100) ? 100 : ((double) totalUserBuy / (double) totalUser) * 100);
        return billRate;
    }

    @Override
    public BillRate getBills(SearchThongKeDTO searchThongKeDTO) {
        try {
            searchThongKeDTO.setDate((searchThongKeDTO.getDate() == null || "".equals(searchThongKeDTO.getDate())) ? sdf.format(new Date()) : sdf.format(sdf.parse(searchThongKeDTO.getDate())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String[] _time = searchThongKeDTO.getDate().split("-");
        BillRate billRate = new BillRate();
        Long day = (searchThongKeDTO.getShowType() == null || searchThongKeDTO.getShowType() == -1  || searchThongKeDTO.getShowType() == 2  || searchThongKeDTO.getShowType() == 3) ? -1 : Long.parseLong(_time[2]);
        Long month = (searchThongKeDTO.getShowType() == null || searchThongKeDTO.getShowType() == -1 || searchThongKeDTO.getShowType() == 1) ? -1 : Long.parseLong(_time[1]);
        Long year = (searchThongKeDTO.getShowType() == null || searchThongKeDTO.getShowType() == -1 || searchThongKeDTO.getShowType() == 1  || searchThongKeDTO.getShowType() == 2) ? -1 : Long.parseLong(_time[0]);
        long totalBill = thongKeRepo.totalBill(null,day,month,year);
        Long totalBillAtStore = thongKeRepo.totalBill(1,day,month,year);
        Long totalBillOnline = thongKeRepo.totalBill(2,day,month,year);
        Long totalBillAtStoreDangCho = thongKeRepo.totalBill(1, 1,null,null,null);
        Long totalBillAtStoreDangHoanThanh = thongKeRepo.totalBill(1, 5,null,null,null);
        Long totalBillOnlineChoXacNhan = thongKeRepo.totalBill(2,1,null,null,null);
        Long totalBillOnlineChoGiaoHang = thongKeRepo.totalBill(2,3,null,null,null);
        Long totalBillOnlineDangGiaoHang = thongKeRepo.totalBill(2,4,null,null,null);
        Long totalBillOnlineDaGiao = thongKeRepo.totalBill(2,5,null,null,null);
        Long totalBillOnlineDaHuy = thongKeRepo.totalBill(2,0,null,null,null);

        List<Object[]> sumBillAtStore = new ArrayList<>();
        List<Object[]> sumBillOnline = new ArrayList<>();

        if (searchThongKeDTO.getShowType() == null) {
            sumBillAtStore = thongKeRepo.getSumBillDay(1, null, null);
            sumBillOnline = thongKeRepo.getSumBillDay(2, null, null);
        } else {
            if (searchThongKeDTO.getShowType() == 1) {
                sumBillAtStore = thongKeRepo.getSumBillDay(1, Long.parseLong(_time[1]), Long.parseLong(_time[0]));
                sumBillOnline = thongKeRepo.getSumBillDay(2, Long.parseLong(_time[1]), Long.parseLong(_time[0]));
            }else if (searchThongKeDTO.getShowType() == -1) {
                sumBillAtStore = thongKeRepo.getSumBillDay(1,null,null);
                sumBillOnline = thongKeRepo.getSumBillDay(2,null,null);
            } else if (searchThongKeDTO.getShowType() == 2) {
                 sumBillAtStore = thongKeRepo.getSumBillMonth(1, Long.parseLong(_time[0]));
                 sumBillOnline = thongKeRepo.getSumBillMonth(2, Long.parseLong(_time[0]));
            } else {
                 sumBillAtStore = thongKeRepo.getSumBillYear(1);
                 sumBillOnline = thongKeRepo.getSumBillYear(2);
            }
        }

        List<BillFinal> billFinalsAtStore = new ArrayList<>();
        for (Object[] object : sumBillAtStore) {
            BillFinal bill = new BillFinal();
            bill.setDate((object[0]) + "");
            bill.setTotalMoney(Math.round(Double.parseDouble(object[1] + "")));
            billFinalsAtStore.add(bill);
        }
        List<BillFinal> billFinalsOnline = new ArrayList<>();
        for (Object[] object : sumBillOnline) {
            BillFinal bill = new BillFinal();
            bill.setDate((object[0]) + "");
            bill.setTotalMoney(Math.round(Double.parseDouble(object[1] + "")));
            billFinalsOnline.add(bill);
        }

        billRate.setSumBillStore(billFinalsAtStore);
        billRate.setSumBillOnline(billFinalsOnline);
        billRate.setTotalBillOnlineChoGiaoHang(totalBillOnlineChoGiaoHang);
        billRate.setTotalBillOnlineDangGiao(totalBillOnlineDangGiaoHang);
        billRate.setTotalBillOnlineDaGiao(totalBillOnlineDaGiao);
        billRate.setTotalBillOnlineDaHuy(totalBillOnlineDaHuy);
        billRate.setTotalBillOnlineChoXacNhan(totalBillOnlineChoXacNhan);
        billRate.setTotalBillAtStoreCho(totalBillAtStoreDangCho);
        billRate.setTotalBillAtStoreDaHT(totalBillAtStoreDangHoanThanh);
        billRate.setTotalBillOnline(totalBillOnline);
        billRate.setTotalBillAtStore(totalBillAtStore);
        billRate.setTotalBill(totalBill);
        return billRate;
    }

    public BillRate getProducts(SearchThongKeDTO searchThongKeDTO){
        BillRate billRate = new BillRate();
        Long sumProduct = thongKeRepo.sumProduct(1,null,null);
        Long totalProductSapHet = thongKeRepo.sumProduct(1,10l,0l);
        Long totalProductActive = thongKeRepo.sumProduct(1,null,0l);
        Long totalProductHet = thongKeRepo.sumProduct(1,0l, null);
        Page<Product> pageProductHot = thongKeRepo.pageProductSold(PageRequest.of(searchThongKeDTO.getPage() == null ? 0 : searchThongKeDTO.getPage(),5));
        Page<Product> pageProductnew = thongKeRepo.pageProductNew(PageRequest.of(searchThongKeDTO.getPage2() == null ? 0 : searchThongKeDTO.getPage2(),5));

        billRate.setPageProductHot(pageProductHot);
        billRate.setPageProductNew(pageProductnew);
        billRate.setTotalProductActive(totalProductActive);
        billRate.setTotalProductHet(totalProductHet);
        billRate.setTotalProductSapHet(totalProductSapHet);
        billRate.setSumProduct(sumProduct);
        return billRate;
    }

}
