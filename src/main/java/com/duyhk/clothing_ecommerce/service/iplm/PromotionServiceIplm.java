package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.PromotionDTO;
import com.duyhk.clothing_ecommerce.dto.ReviewProductDTO;
import com.duyhk.clothing_ecommerce.entity.Promotion;
import com.duyhk.clothing_ecommerce.reponsitory.PromotionReponsitory;
import com.duyhk.clothing_ecommerce.service.PromotionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceIplm implements PromotionService {
    @Autowired
    private PromotionReponsitory promotionRepo;

    @Override
    public Promotion convertToEntity(PromotionDTO promotionDTO) {
        return new ModelMapper().map(promotionDTO, Promotion.class);
    }

    @Override
    public PromotionDTO convertToDto(Promotion promotion) {
        return new ModelMapper().map(promotion, PromotionDTO.class);
    }

    @Override
    public List<PromotionDTO> getAll() {
        return promotionRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<PromotionDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Promotion> pageEntity = promotionRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<PromotionDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<PromotionDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PromotionDTO getById(Long id) {
        return convertToDto(promotionRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(PromotionDTO promotionDTO) {
        promotionRepo.save(convertToEntity(promotionDTO));
    }

    @Override
    public void update(PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepo.findById(promotionDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (promotion != null) {
            promotion = convertToEntity(promotionDTO);
            promotionRepo.save(promotion);
        }

    }

    @Override
    public void changeStatus(PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepo.findById(promotionDTO.getId()).orElse(null);
        if(promotion != null){
            promotion.setStatus(promotionDTO.getStatus());
            promotionRepo.save(promotion);
        }
    }

    @Override
    public void delete(Long id) {
        Promotion promotion = promotionRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (promotion != null) {
            promotionRepo.deleteById(id);
        }
    }
}
