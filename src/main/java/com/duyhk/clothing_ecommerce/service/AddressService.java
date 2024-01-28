package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.entity.Address;

import java.util.List;

public interface AddressService {
    Address convertToEntity(AddressDTO addressDTO);

    AddressDTO convertToDto(Address address);

    List<AddressDTO> getAll();

    PageDTO<List<AddressDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PageDTO<List<AddressDTO>> getByUserId(Long id,PageRequestDTO pageRequestDTO);
    AddressDTO getById(Long id);

    void create(AddressDTO addressDTO);

    void update(AddressDTO addressDTO);

    void delete(Long id);
}
