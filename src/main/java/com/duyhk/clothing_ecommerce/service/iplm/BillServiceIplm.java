package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.entity.*;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.reponsitory.BillDetailReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.BillReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.CartReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.UserReponsitory;
import com.duyhk.clothing_ecommerce.service.BillService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BillServiceIplm implements BillService {
    @Autowired
    private BillReponsitory billReponsitory;

    @Autowired
    private BillDetailReponsitory billDetailRepo;

    @Autowired
    private UserReponsitory userRepon;

    @Autowired
    private CartReponsitory cartRepo;

    @Override
    public Bill convertToEntity(BillDTO billDTO) {
        return new ModelMapper().map(billDTO, Bill.class);
    }

    @Override
    public BillDTO convertToDto(Bill bill) {
        return new ModelMapper().map(bill, BillDTO.class);
    }

    @Override
    public List<BillDTO> getAll() {
        return billReponsitory.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<BillDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Bill> pageEntity = billReponsitory.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<BillDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<BillDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<BillDTO>> search(SearchBillDTO searchBillDTO) {
        searchBillDTO.setPage(searchBillDTO.getPage() == null ? 0 : searchBillDTO.getPage());
        searchBillDTO.setSize(searchBillDTO.getSize() == null ? 5 : searchBillDTO.getSize());
        Page<Bill> pageEntity = billReponsitory.search(
                PageRequest.of(
                        searchBillDTO.getPage(),
                        searchBillDTO.getSize()),
                searchBillDTO.getStatus(),
                searchBillDTO.getDateStart(),
                searchBillDTO.getDateEnd(),
                 searchBillDTO.getStaff(),
                searchBillDTO.getPhoneNumber());
        List<BillDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<BillDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<BillDTO>> getSellAtStore(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Bill> pageEntity = billReponsitory.getSellAtStore(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<BillDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<BillDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<BillDTO>> getByStatus(PageRequestDTO pageRequestDTO, Integer status) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Bill> pageEntity = billReponsitory.getByStatus(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()),
                status);
        List<BillDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<BillDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public BillDTO getById(Long id) {
        return convertToDto(billReponsitory.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public Bill createAtStore(BillDTO billDTO) {
        Bill bill = convertToEntity(billDTO);
        bill.setStatus(1);
        bill.setTotalMoney(0.0);
        bill.setTatolProduct(0L);
        bill.setBillType(1);
        bill.setBillCode(generateRandomString());
        bill.setOrderDate(bill.getCreatedAt());
        return billReponsitory.save(bill);
    }

    @Override
    public void updateSellAtStoreFinal(BillDTO billDTO) {
        Bill bill = billReponsitory.findById(billDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (bill != null) {
            bill.setStatus(billDTO.getStatus());
            if (billDTO.getUser().getPhoneNumber() == null) {
                throw new CustomValidationException("Vui lòng nhập số điện thoại");
            }
            Users user = userRepon.findByPhoneNumber(billDTO.getUser().getPhoneNumber()).orElse(null);
            if (user != null) {
                bill.setUser(user);
                bill.setPhoneNumber(billDTO.getUser().getPhoneNumber());
            }
            else{
                if(billDTO.getUser() == null || billDTO.getUser().getPhoneNumber() == null || "".equals(billDTO.getUser().getPhoneNumber())){
                    bill.setFullName("Khách lẻ");
                    billReponsitory.save(bill);
                }else{
                    bill.setPhoneNumber(billDTO.getPhoneNumber() == null ? billDTO.getUser().getPhoneNumber() : billDTO.getPhoneNumber());
                    bill.setFullName(billDTO.getFullName() == null ? billDTO.getUser().getPhoneNumber() : billDTO.getFullName());
                    bill.setOrderDateFinal(bill.getStatus() == 5 ? new Date() : null);
                    Bill b = billReponsitory.save(bill);
                    if (user == null) {
                        cartRepo.save(new Cart(0L, 0.0, b.getUser()));
                    }
                }
            }

        }
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        Bill bill = billReponsitory.findById(id).orElse(null);
        if (bill != null) {
            bill.setOrderDateFinal(status == 5 ? new Date() : null);
            bill.setStatus(status);
            billReponsitory.save(bill);
        }
    }

    @Override
    public void delete(Long id) {
        Bill bill = billReponsitory.findById(id).orElseThrow(IllegalArgumentException::new);
        if (bill != null) {
            billReponsitory.deleteById(id);
        }
    }

    @Override
    public void cancelBill(Long id) {
        Bill bill = billReponsitory.findById(id).orElseThrow(IllegalArgumentException::new);
        bill.setStatus(0);
        List<BillDetail> billDetailList = billDetailRepo.findByBillId(id);
        billDetailList.stream().forEach(billDetail -> {
            ProductDetail productDetail = billDetail.getProductDetail();
            Product product = productDetail.getProduct();
            productDetail.setQuantitySold(productDetail.getQuantitySold() - billDetail.getQuantity());
            product.setTotalQuantitySold(product.getTotalQuantitySold() - billDetail.getQuantity());
            productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity());
            product.setTotalQuantity(product.getTotalQuantity() + billDetail.getQuantity());
        });
        billReponsitory.save(bill);
    }

    //utils
    public String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return "HD" + randomString.toString();
    }
}
