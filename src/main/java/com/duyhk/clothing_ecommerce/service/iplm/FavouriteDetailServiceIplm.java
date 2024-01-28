package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.FavouriteDetail;
import com.duyhk.clothing_ecommerce.reponsitory.FavouriteDetailReponsitory;
import com.duyhk.clothing_ecommerce.service.FavouriteDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteDetailServiceIplm implements FavouriteDetailService {
    @Autowired
    private FavouriteDetailReponsitory favouriteDetailRepo;

    @Override
    public FavouriteDetail convertToEntity(FavouriteDetailDTO favouriteDetailDTO) {
        return new ModelMapper().map(favouriteDetailDTO, FavouriteDetail.class);
    }

    @Override
    public FavouriteDetailDTO convertToDto(FavouriteDetail favouriteDetail) {
        return new ModelMapper().map(favouriteDetail, FavouriteDetailDTO.class);
    }

    @Override
    public List<FavouriteDetailDTO> getAll() {
        return favouriteDetailRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<FavouriteDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<FavouriteDetail> pageEntity = favouriteDetailRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<FavouriteDetailDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<FavouriteDetailDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public FavouriteDetailDTO getById(Long id) {
        return convertToDto(favouriteDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(FavouriteDetailDTO favouriteDetailDTO) {
        favouriteDetailRepo.save(convertToEntity(favouriteDetailDTO));
    }

    @Override
    public void update(FavouriteDetailDTO favouriteDetailDTO) {
        FavouriteDetail favouriteDetail = favouriteDetailRepo.findById(favouriteDetailDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (favouriteDetail != null) {
            favouriteDetail = convertToEntity(favouriteDetailDTO);
            favouriteDetailRepo.save(favouriteDetail);
        }

    }

    @Override
    public void delete(Long id) {
        FavouriteDetail favouriteDetail = favouriteDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (favouriteDetail != null) {
            favouriteDetailRepo.deleteById(id);
        }
    }
}
