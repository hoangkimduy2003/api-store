package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.VoucherDTO;
import com.duyhk.clothing_ecommerce.entity.Size;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.entity.Voucher;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.reponsitory.UserReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.VoucherReponsitory;
import com.duyhk.clothing_ecommerce.service.VoucherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class VoucherServiceIplm implements VoucherService {

    @Autowired
    private VoucherReponsitory voucherRepo;

    @Autowired
    private UserReponsitory userRepo;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public Voucher convertToEntity(VoucherDTO voucherDTO) {
        return new ModelMapper().map(voucherDTO, Voucher.class);
    }

    @Override
    public VoucherDTO convertToDto(Voucher voucher) {
        return new ModelMapper().map(voucher, VoucherDTO.class);
    }

    @Override
    public List<VoucherDTO> getAll() {
        return voucherRepo.findAll().stream().map(v -> convertToDto(v)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<VoucherDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Voucher> pageEntity = voucherRepo.findAll(PageRequest.of(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize()
        ));
        List<VoucherDTO> list = pageEntity.getContent().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
        return PageDTO.<List<VoucherDTO>>builder()
                .data(list)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<VoucherDTO>> getByKh(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Voucher> pageEntity = voucherRepo.getKh(PageRequest.of(
                pageRequestDTO.getPage(),
                pageRequestDTO.getSize()
        ));
        List<VoucherDTO> list = pageEntity.getContent().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
        return PageDTO.<List<VoucherDTO>>builder()
                .data(list)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public VoucherDTO getById(Long id) {
        return convertToDto(voucherRepo.findById(id).orElse(null));
    }

    @Override
    public void create(VoucherDTO voucherDTO) {
        voucherRepo.save(convertToEntity(voucherDTO));
    }

    @Override
    public void update(VoucherDTO voucherDTO) {
        Voucher voucher = voucherRepo.findById(voucherDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (voucher != null) {
            voucher = convertToEntity(voucherDTO);
            voucherRepo.save(voucher);
        }
    }

    @Override
    public void delete(Long id) {
        voucherRepo.deleteById(id);
    }

    @Override
    public void action(VoucherDTO voucherDTO) {
        Voucher voucher = convertToEntity(voucherDTO);
        voucher.setSendType(1);
        if(voucherDTO.getId() == null){
            voucher.setDiscount(0d);
        }
        if(voucher.getVoucherType() == 2){
            voucher.setDiscount(0d);
            voucher.setMaximumPromotion(voucher.getPromotionalLevel());
        }
        voucherRepo.save(voucher);
    }

    @Override
    public VoucherDTO detail(Long id) {
        return convertToDto(voucherRepo.findById(id).orElse(null));
    }

    @Override
    public void sendEmail(Long id) {
        Voucher voucher = voucherRepo.findById(id).orElse(null);
        String titleEmail = "OMAN CHI ÂN KHÁCH HÀNG MÃ GIẢM GIÁ";
        String contentEmail = """
                   <h4>OMAN cảm ơn %s đã đồng hàng cùng chúng tôi<h4>
                   <h5>Mã giảm giá: %s</h5>
                   <h6>Giảm %s cho đơn hàng từ %s nhanh tay đặt hàng ngay </h6>
                """;
        List<Users> usersList = userRepo.findUserHaveEmail();
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        for (Users u : usersList){
            emailService.sendEmail(u.getEmail(),titleEmail,
                    String.format(contentEmail,u.getFullName(),
                            voucher.getVoucherCode(),
                            format.format(voucher.getPromotionalLevel()) + (voucher.getVoucherType() == 1 ? "%" : " nghìn đồng"),
                            format.format(voucher.getMinimumInvoice())));
        }
        voucher.setSendType(2);
        voucherRepo.save(voucher);
    }
    @Override
    public VoucherDTO findByVoucherCode(String voucherCode){
        VoucherDTO voucherDTO = convertToDto(voucherRepo.findByVoucherCode(voucherCode).orElseThrow(() -> new CustomValidationException("voucher not found")));
        if(voucherDTO.getStatus() == 0) throw new CustomValidationException("voucher not active");
        if(voucherDTO.getDateStart().compareTo(LocalDate.now()) > 0) throw new CustomValidationException("Voucher not active");
        if(voucherDTO.getDateEnd().compareTo(LocalDate.now()) < 0) throw new CustomValidationException("Voucher not active");
        return voucherDTO;
    }
}
