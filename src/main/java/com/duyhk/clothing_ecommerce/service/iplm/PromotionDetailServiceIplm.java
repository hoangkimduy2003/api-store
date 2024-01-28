package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.entity.PromotionDetail;
import com.duyhk.clothing_ecommerce.reponsitory.PromotionDetailReponsitory;
import com.duyhk.clothing_ecommerce.service.PromotionDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionDetailServiceIplm implements PromotionDetailService {
    @Autowired
    private PromotionDetailReponsitory promotionDetailRepo;

    @Override
    public PromotionDetail convertToEntity(PromotionDetailDTO promotionDetailDTO) {
        return new ModelMapper().map(promotionDetailDTO, PromotionDetail.class);
    }

    @Override
    public PromotionDetailDTO convertToDto(PromotionDetail promotionDetail) {
        return new ModelMapper().map(promotionDetail, PromotionDetailDTO.class);
    }

    @Override
    public List<PromotionDetailDTO> getAll() {
        return promotionDetailRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<PromotionDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<PromotionDetail> pageEntity = promotionDetailRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<PromotionDetailDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<PromotionDetailDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PromotionDetailDTO getById(Long id) {
        return convertToDto(promotionDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(PromotionDetailDTO promotionDetailDTO) {
        promotionDetailRepo.save(convertToEntity(promotionDetailDTO));
    }

    @Override
    public void update(PromotionDetailDTO promotionDetailDTO) {
        PromotionDetail promotionDetail = promotionDetailRepo.findById(promotionDetailDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (promotionDetail != null) {
            promotionDetail = convertToEntity(promotionDetailDTO);
            promotionDetailRepo.save(promotionDetail);
        }

    }

    @Override
    public void delete(Long id) {
        PromotionDetail promotionDetail = promotionDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (promotionDetail != null) {
            promotionDetailRepo.deleteById(id);
        }
    }
}
