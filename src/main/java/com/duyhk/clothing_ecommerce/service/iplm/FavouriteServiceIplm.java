package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Favourite;
import com.duyhk.clothing_ecommerce.entity.FavouriteDetail;
import com.duyhk.clothing_ecommerce.reponsitory.FavouriteReponsitory;
import com.duyhk.clothing_ecommerce.service.FavouriteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceIplm implements FavouriteService {
    @Autowired
    private FavouriteReponsitory favouriteRepo;

    @Override
    public Favourite convertToEntity(FavouriteDTO favouriteDTO) {
        return new ModelMapper().map(favouriteDTO, Favourite.class);
    }

    @Override
    public FavouriteDTO convertToDto(Favourite favourite) {
        return new ModelMapper().map(favourite, FavouriteDTO.class);
    }

    @Override
    public List<FavouriteDTO> getAll() {
        return favouriteRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<FavouriteDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Favourite> pageEntity = favouriteRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<FavouriteDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<FavouriteDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public FavouriteDTO getById(Long id) {
        return convertToDto(favouriteRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(FavouriteDTO favouriteDTO) {
        favouriteRepo.save(convertToEntity(favouriteDTO));
    }

    @Override
    public void update(FavouriteDTO favouriteDTO) {
        Favourite favourite = favouriteRepo.findById(favouriteDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (favourite != null) {
            favourite = convertToEntity(favouriteDTO);
            favouriteRepo.save(favourite);
        }

    }

    @Override
    public void delete(Long id) {
        Favourite favourite = favouriteRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (favourite != null) {
            favouriteRepo.deleteById(id);
        }
    }
}
