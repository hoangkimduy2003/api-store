package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Address;
import com.duyhk.clothing_ecommerce.entity.Color;
import com.duyhk.clothing_ecommerce.reponsitory.ColorReponsitory;
import com.duyhk.clothing_ecommerce.service.ColorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorServiceIplm implements ColorService {

    @Autowired
    private ColorReponsitory colorRepo;

    @Override
    public Color convertToEntity(ColorDTO colorDTO) {
        return new ModelMapper().map(colorDTO, Color.class);
    }

    @Override
    public ColorDTO convertToDto(Color color) {
        return new ModelMapper().map(color, ColorDTO.class);
    }

    @Override
    public List<ColorDTO> getAll() {
        return colorRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<ColorDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Color> pageEntity = colorRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ColorDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<ColorDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public ColorDTO getById(Long id) {
        return convertToDto(colorRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(ColorDTO colorDTO) {
        colorRepo.save(convertToEntity(colorDTO));
    }

    @Override
    public void update(ColorDTO colorDTO) {
        Color color = colorRepo.findById(colorDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (color != null) {
            color = convertToEntity(colorDTO);
            colorRepo.save(color);
        }

    }

    @Override
    public void delete(Long id) {
        Color color = colorRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (color != null) {
            colorRepo.deleteById(id);
        }
    }

}
