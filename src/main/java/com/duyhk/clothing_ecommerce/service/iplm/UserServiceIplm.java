package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.UserDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchUserDTO;
import com.duyhk.clothing_ecommerce.entity.Cart;
import com.duyhk.clothing_ecommerce.entity.Favourite;
import com.duyhk.clothing_ecommerce.entity.Role;
import com.duyhk.clothing_ecommerce.entity.Users;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.reponsitory.CartReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.UserReponsitory;
import com.duyhk.clothing_ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIplm implements UserService {
    @Autowired
    private UserReponsitory userRepo;

    @Autowired
    private CartReponsitory cartRepo;

    @Override
    public Users convertToEntity(UserDTO userDTO) {
        return new ModelMapper().map(userDTO, Users.class);
    }

    @Override
    public UserDTO convertToDto(Users user) {
        return new ModelMapper().map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<UserDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Users> pageEntity = userRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<UserDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<UserDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<UserDTO>> searchCustomer(SearchUserDTO searchUserDTO) {
        searchUserDTO.setPage(searchUserDTO.getPage() == null ? 0 : searchUserDTO.getPage());
        searchUserDTO.setSize(searchUserDTO.getSize() == null ? 5 : searchUserDTO.getSize());
        Page<Users> pageEntity = userRepo.getCustomer(
                searchUserDTO.getPhoneNumber(),
                PageRequest.of(
                        searchUserDTO.getPage(),
                        searchUserDTO.getSize()));
        List<UserDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<UserDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public UserDTO getById(Long id) {
        return convertToDto(userRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public Users findByPhoneNumber(String phoneNumber) {
        return userRepo.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomValidationException("User not found"));
    }

    @Override
    public void create(UserDTO userDTO) {
        Users users = convertToEntity(userDTO);
        users.setPassword((userDTO.getPassword()));
        users.setFavourite(new Favourite());
        if (users.getRole() == null || "".equals(users.getRole())) {
            users.setRole(Role.CUSTOMER);
        }
        Users u = userRepo.save(users);
        cartRepo.save(new Cart(0L, 0.0, u));
    }

    @Override
    public void update(UserDTO userDTO) {
        Users user = userRepo.findById(userDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (user != null) {
            user = convertEndecorUser(user, userDTO);
            userRepo.save(user);
        }

    }

    @Override
    public void delete(Long id) {
        Users user = userRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (user != null) {
            userRepo.deleteById(id);
        }
    }

    public Users convertEndecorUser(Users user, UserDTO userDTO) {
        user.setPassword((userDTO.getPassword()));
        user.setImage(user.getImage());
        user.setFullName(userDTO.getFullName());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setFavourite(new Favourite());
        return user;
    }
}
