package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.AddressDTO;
import com.duyhk.clothing_ecommerce.dto.BillDetailDTO;
import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.entity.Address;
import com.duyhk.clothing_ecommerce.reponsitory.AddressReponsitory;
import com.duyhk.clothing_ecommerce.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceIplm implements AddressService {

    @Autowired
    private AddressReponsitory addressReponsitory;

    @Override
    public Address convertToEntity(AddressDTO addressDTO) {
        return new ModelMapper().map(addressDTO, Address.class);
    }

    @Override
    public AddressDTO convertToDto(Address address) {
        return new ModelMapper().map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAll() {
        return addressReponsitory.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<AddressDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Address> pageEntity = addressReponsitory.findAll(PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize()));
        List<AddressDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<AddressDTO>>builder().data(listDto).totalElements(pageEntity.getTotalElements()).totalPages(pageEntity.getTotalPages()).build();
    }

    @Override
    public PageDTO<List<AddressDTO>> getByUserId(Long id, PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Address> pageEntity = addressReponsitory.findByUserId(id, PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize()));
        List<AddressDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<AddressDTO>>builder().data(listDto).totalElements(pageEntity.getTotalElements()).totalPages(pageEntity.getTotalPages()).build();
    }

    @Override
    public AddressDTO getById(Long id) {
        return convertToDto(addressReponsitory.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(AddressDTO addressDTO) {
        addressReponsitory.save(convertToEntity(addressDTO));
    }

    @Override
    public void update(AddressDTO addressDTO) {
        Address address = addressReponsitory.findById(addressDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (address != null) {
            address = convertToEntity(addressDTO);
            addressReponsitory.save(address);
        }

    }

    @Override
    public void delete(Long id) {
        Address address = addressReponsitory.findById(id).orElseThrow(IllegalArgumentException::new);
        if (address != null) {
            addressReponsitory.deleteById(id);
        }
    }
}
