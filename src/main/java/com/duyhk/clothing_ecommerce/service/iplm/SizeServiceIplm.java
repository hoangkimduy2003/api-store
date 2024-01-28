package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.ReviewProduct;
import com.duyhk.clothing_ecommerce.entity.Size;
import com.duyhk.clothing_ecommerce.reponsitory.SizeReponsitory;
import com.duyhk.clothing_ecommerce.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceIplm implements SizeService {
    @Autowired
    private SizeReponsitory sizeRepo;

    @Override
    public Size convertToEntity(SizeDTO sizeDTO) {
        return new ModelMapper().map(sizeDTO, Size.class);
    }

    @Override
    public SizeDTO convertToDto(Size size) {
        return new ModelMapper().map(size, SizeDTO.class);
    }

    @Override
    public List<SizeDTO> getAll() {
        return sizeRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<SizeDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Size> pageEntity = sizeRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<SizeDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<SizeDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public SizeDTO getById(Long id) {
        return convertToDto(sizeRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(SizeDTO sizeDTO) {
        sizeRepo.save(convertToEntity(sizeDTO));
    }

    @Override
    public void update(SizeDTO sizeDTO) {
        Size size = sizeRepo.findById(sizeDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (size != null) {
            size = convertToEntity(sizeDTO);
            sizeRepo.save(size);
        }

    }

    @Override
    public void delete(Long id) {
        Size size = sizeRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (size != null) {
            sizeRepo.deleteById(id);
        }
    }
}
