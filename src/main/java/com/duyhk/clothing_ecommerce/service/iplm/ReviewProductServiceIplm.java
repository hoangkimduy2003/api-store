package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ReviewProductDTO;
import com.duyhk.clothing_ecommerce.dto.SizeDTO;
import com.duyhk.clothing_ecommerce.entity.ReviewProduct;
import com.duyhk.clothing_ecommerce.reponsitory.ReviewProductReponsitory;
import com.duyhk.clothing_ecommerce.service.ReviewProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewProductServiceIplm implements ReviewProductService {
    @Autowired
    private ReviewProductReponsitory reviewProductRepo;

    @Override
    public ReviewProduct convertToEntity(ReviewProductDTO reviewProductDTO) {
        return new ModelMapper().map(reviewProductDTO, ReviewProduct.class);
    }

    @Override
    public ReviewProductDTO convertToDto(ReviewProduct reviewProduct) {
        return new ModelMapper().map(reviewProduct, ReviewProductDTO.class);
    }

    @Override
    public List<ReviewProductDTO> getAll() {
        return reviewProductRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<ReviewProductDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<ReviewProduct> pageEntity = reviewProductRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ReviewProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<ReviewProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public ReviewProductDTO getById(Long id) {
        return convertToDto(reviewProductRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(ReviewProductDTO reviewProductDTO) {
        reviewProductRepo.save(convertToEntity(reviewProductDTO));
    }

    @Override
    public void update(ReviewProductDTO reviewProductDTO) {
        ReviewProduct reviewProduct = reviewProductRepo.findById(reviewProductDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (reviewProduct != null) {
            reviewProduct = convertToEntity(reviewProductDTO);
            reviewProductRepo.save(reviewProduct);
        }

    }

    @Override
    public void delete(Long id) {
        ReviewProduct reviewProduct = reviewProductRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (reviewProduct != null) {
            reviewProductRepo.deleteById(id);
        }
    }
}
