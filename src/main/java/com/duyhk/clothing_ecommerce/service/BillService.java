package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BillService {
    Bill convertToEntity(BillDTO billDTO);

    BillDTO convertToDto(Bill bill);

    List<BillDTO> getAll();

    PageDTO<List<BillDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PageDTO<List<BillDTO>> search(SearchBillDTO searchBillDTO);

    PageDTO<List<BillDTO>> searchAtStore(SearchBillDTO searchBillDTO);

    PageDTO<List<BillDTO>> getSellAtStore(PageRequestDTO pageRequestDTO);
    PageDTO<List<BillDTO>> getByStatus(PageRequestDTO pageRequestDTO, Integer status);

    BillDTO getById(Long id);

    Bill createAtStore(BillDTO billDTO);

    void updateSellAtStoreFinal(BillDTO billDTO);

    void updateStatusById(Long id, Integer status);

    void createBillOnline(BillDTO billDTO);

    void delete(Long id);

    void cancelBill(Long id);

    // tong hoa don
    Long SoLuongBill();

    // tong doanh thu
    Double doanhThuBill();

    // tong san pham da ban
    Integer sanPhamDaBanBill();

    // sap xep bill
    PageDTO<List<BillDTO>> getCreaterBill(PageRequestDTO pageRequestDTO);

    // hoa don dang giao
    Long pendingInvoice();
    // hoa don da giao
    Long billTransacted();
    // hoa don huy
    Long billCancelled();
    // doanh thu
    Double revenueDay(int day,int month, int year);

    Double revenueMonth(int month, int year);

    Double revenueYear(int year);

// bieu do
    // thong ke
    List<BillDTO> chartbyYear(int year);

    List<ArrayList<Integer>> getMonth(int year);
    List<ArrayList<Integer>> getMoney(int year);
}
