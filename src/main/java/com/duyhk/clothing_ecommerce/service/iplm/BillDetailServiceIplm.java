package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.BillDTO;
import com.duyhk.clothing_ecommerce.dto.BillDetailDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Bill;
import com.duyhk.clothing_ecommerce.entity.BillDetail;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.reponsitory.BillDetailReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.BillReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.ProductDetailReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.ProductReponsitory;
import com.duyhk.clothing_ecommerce.service.BillDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillDetailServiceIplm implements BillDetailService {
    @Autowired
    private BillDetailReponsitory billDetailReponsitory;

    @Autowired
    private ProductDetailReponsitory productDetailRepo;

    @Autowired
    private BillReponsitory billRepo;

    @Override
    public BillDetail convertToEntity(BillDetailDTO billDetailDTO) {
        return new ModelMapper().map(billDetailDTO, BillDetail.class);
    }

    @Override
    public BillDetailDTO convertToDto(BillDetail billDetail) {
        return new ModelMapper().map(billDetail, BillDetailDTO.class);
    }

    @Override
    public List<BillDetailDTO> getAllByBillId(Long id) {
        return billDetailReponsitory.getByBillIdNoPage(id).stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public List<BillDetailDTO> getAll() {
        return billDetailReponsitory.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<BillDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<BillDetail> pageEntity = billDetailReponsitory.findAll(
                PageRequest.of(pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<BillDetailDTO> listDto = pageEntity.get().map(b -> convertToDto(b)).collect(Collectors.toList());
        return PageDTO.<List<BillDetailDTO>>builder()
                .data(listDto)
                .totalPages(pageEntity.getTotalPages())
                .totalElements(pageEntity.getTotalElements())
                .build();
    }

    @Override
    public PageDTO<List<BillDetailDTO>> getByBillId(Long id, PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<BillDetail> pageEntity = billDetailReponsitory.getByBillId(
                id, PageRequest.of(pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<BillDetailDTO> listDto = pageEntity.get().map(b -> convertToDto(b)).collect(Collectors.toList());
        return PageDTO.<List<BillDetailDTO>>builder()
                .data(listDto)
                .totalPages(pageEntity.getTotalPages())
                .totalElements(pageEntity.getTotalElements())
                .build();
    }

    @Override
    public BillDetailDTO getById(Long id) {
        return convertToDto(billDetailReponsitory.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(BillDetailDTO billDetailDTO) {
        List<BillDetail> listAllBillDetailByBill = billDetailReponsitory.getByBillIdNoPage(billDetailDTO.getBill().getId());
        ProductDetail productDetailOld = null;
        if(billDetailDTO.getProductDetail().getId() == null){
            productDetailOld = productDetailRepo.findProductDetailByProductDetailCode(billDetailDTO.getProductDetail().getProductDetailCode());
            if(productDetailOld == null){
                throw new CustomValidationException("Không tìm thấy sản phẩm");
            }
            if(billDetailDTO.getQuantity() > productDetailOld.getQuantity()){
                throw new CustomValidationException("Chỉ còn "+ productDetailOld.getQuantity() + " sản phẩm");
            }
            billDetailDTO.getProductDetail().setId(productDetailOld.getId());
            billDetailDTO.setPrice(productDetailOld.getPriceSale());
            billDetailDTO.setTotalPrice(productDetailOld.getPrice() * billDetailDTO.getQuantity());
        }else{
            productDetailOld = productDetailRepo.findById(billDetailDTO.getProductDetail().getId()).orElse(null);
        }
        billDetailDTO.setPrice(productDetailOld.getPriceSale());
        billDetailDTO.setTotalPrice(billDetailDTO.getPrice() * billDetailDTO.getQuantity());
        BillDetail billDetailCompe = convertToEntity(billDetailDTO);
        for (BillDetail billDetail : listAllBillDetailByBill) {
            if (billDetail.getProductDetail().getId() == billDetailDTO.getProductDetail().getId()) {
                billDetailCompe = billDetail;
                billDetailCompe.setQuantity(billDetail.getQuantity() + billDetailDTO.getQuantity());
                billDetailCompe.setTotalPrice(billDetailCompe.getTotalPrice() + billDetailDTO.getTotalPrice());
            }
        }
        productDetailOld.getProduct().setTotalQuantity( productDetailOld.getProduct().getTotalQuantity() - billDetailDTO.getQuantity());
        productDetailOld.getProduct().setTotalQuantitySold( productDetailOld.getProduct().getTotalQuantitySold() + billDetailDTO.getQuantity());
        productDetailOld.setQuantity(productDetailOld.getQuantity() - billDetailDTO.getQuantity());
        productDetailOld.setQuantitySold(productDetailOld.getQuantitySold() + billDetailDTO.getQuantity());

        Bill bill = billRepo.findById(billDetailDTO.getBill().getId()).orElse(null);
        bill.setTotalMoney(bill.getTotalMoney() + billDetailDTO.getTotalPrice());
        bill.setTatolProduct(bill.getTatolProduct() + billDetailDTO.getQuantity());
        billDetailReponsitory.save(billDetailCompe);
    }

    @Override
    public void update(BillDetailDTO billDetailDTO) {
        BillDetail billDetail = billDetailReponsitory.findById(billDetailDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (billDetail != null) {
            Bill bill = billDetail.getBill();
            ProductDetail productDetail = billDetail.getProductDetail();
            productDetail.getProduct().setTotalQuantity(productDetail.getProduct().getTotalQuantity() + billDetail.getQuantity() - billDetailDTO.getQuantity());
            productDetail.getProduct().setTotalQuantitySold(productDetail.getProduct().getTotalQuantitySold() - billDetail.getQuantity() + billDetailDTO.getQuantity());
            productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity() - billDetailDTO.getQuantity());
            productDetail.setQuantitySold(productDetail.getQuantitySold() - billDetail.getQuantity() + billDetailDTO.getQuantity());
            billDetailDTO.setTotalPrice(billDetailDTO.getPrice() * billDetailDTO.getQuantity());
            bill.setTotalMoney(billDetailDTO.getTotalPrice() + bill.getTotalMoney() - billDetail.getTotalPrice());
            bill.setTatolProduct(bill.getTatolProduct() + billDetailDTO.getQuantity() - billDetail.getQuantity());
            billDetail = convertToEntity(billDetailDTO);
            billDetailReponsitory.save(billDetail);
        }

    }

    @Override
    public void delete(Long id) {
        BillDetail billDetail = billDetailReponsitory.findById(id).orElseThrow(IllegalArgumentException::new);
        if (billDetail != null) {
            Bill bill = billDetail.getBill();
            ProductDetail productDetail = billDetail.getProductDetail();
            productDetail.getProduct().setTotalQuantity(productDetail.getProduct().getTotalQuantity() + billDetail.getQuantity());
            productDetail.getProduct().setTotalQuantitySold(productDetail.getProduct().getTotalQuantitySold() - billDetail.getQuantity());
            productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity() );
            productDetail.setQuantitySold(productDetail.getQuantitySold() - billDetail.getQuantity());
            bill.setTotalMoney(bill.getTotalMoney() - billDetail.getTotalPrice());
            bill.setTatolProduct(bill.getTatolProduct() - billDetail.getQuantity());
            
            billDetailReponsitory.deleteById(id);
        }
    }

    // utils

}
