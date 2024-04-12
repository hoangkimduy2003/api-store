package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.VoucherDTO;
import com.duyhk.clothing_ecommerce.entity.Voucher;

import java.util.List;

public interface VoucherService {
    Voucher convertToEntity(VoucherDTO voucherDTO);

    VoucherDTO convertToDto(Voucher voucher);

    List<VoucherDTO> getAll();

    PageDTO<List<VoucherDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PageDTO<List<VoucherDTO>> getByKh(PageRequestDTO pageRequestDTO);

    VoucherDTO getById(Long id);

    void create(VoucherDTO voucherDTO);

    void update(VoucherDTO voucherDTO);

    void delete(Long id);

    void action(VoucherDTO voucherDTO);

    VoucherDTO detail(Long id);

    void sendEmail(Long id);

    VoucherDTO findByVoucherCode(String voucherCode);
}
