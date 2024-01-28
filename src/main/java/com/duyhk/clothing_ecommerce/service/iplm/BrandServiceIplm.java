package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.BrandDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Brand;
import com.duyhk.clothing_ecommerce.reponsitory.BrandReponsitory;
import com.duyhk.clothing_ecommerce.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceIplm implements BrandService {
    @Autowired
    private BrandReponsitory brandRepo;

    @Override
    public Brand convertToEntity(BrandDTO brandDTO) {
        return new ModelMapper().map(brandDTO, Brand.class);
    }

    @Override
    public BrandDTO convertToDto(Brand brand) {
        return new ModelMapper().map(brand, BrandDTO.class);
    }

    @Override
    public List<BrandDTO> getAll() {
        return brandRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<BrandDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Brand> pageEntity = brandRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<BrandDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<BrandDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public BrandDTO getById(Long id) {
        return convertToDto(brandRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(BrandDTO brandDTO) {
        brandRepo.save(convertToEntity(brandDTO));
    }

    @Override
    public void update(BrandDTO brandDTO) {
        Brand brand = brandRepo.findById(brandDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (brand != null) {
            brand = convertToEntity(brandDTO);
            brandRepo.save(brand);
        }

    }

    @Override
    public void delete(Long id) {
        Brand brand = brandRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (brand != null) {
            brandRepo.deleteById(id);
        }
    }
}
