package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.entity.Favourite;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import com.duyhk.clothing_ecommerce.reponsitory.ProductDetailReponsitory;
import com.duyhk.clothing_ecommerce.service.ProductDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailServiceIplm implements ProductDetailService {
    @Autowired
    private ProductDetailReponsitory productDetailRepo;

    @Override
    public ProductDetail convertToEntity(ProductDetailDTO productDetailDTO) {
        return new ModelMapper().map(productDetailDTO, ProductDetail.class);
    }

    @Override
    public ProductDetailDTO convertToDto(ProductDetail productDetail) {
        return new ModelMapper().map(productDetail, ProductDetailDTO.class);
    }

    @Override
    public List<ProductDetailDTO> getAll() {
        return productDetailRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<ProductDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<ProductDetail> pageEntity = productDetailRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ProductDetailDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList())
                .stream().filter(p -> p.getStatus() == 1 && p.getQuantity() > 0).collect(Collectors.toList());
        return PageDTO.<List<ProductDetailDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<ProductDetailDTO>> search(SearchProductDTO searchProductDTO) {
        searchProductDTO.setPage(searchProductDTO.getPage() == null ? 0 : searchProductDTO.getPage());
        searchProductDTO.setSize(searchProductDTO.getSize() == null ? 5 : searchProductDTO.getSize());
        Page<ProductDetail> pageEntity = productDetailRepo.search(
                "%" + (searchProductDTO.getName() != null ? searchProductDTO.getName() : "") + "%",
                "%" + (searchProductDTO.getProductDetailCode() != null ? searchProductDTO.getProductDetailCode() : "") + "%",
                searchProductDTO.getBrandId(),
                searchProductDTO.getStatus(),
                searchProductDTO.getCategoryId(),
                PageRequest.of(
                        searchProductDTO.getPage(),
                        searchProductDTO.getSize()));
        List<ProductDetailDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<ProductDetailDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public ProductDetailDTO findProductDetailByProductDetailCode(String productDetailCode) {
        return convertToDto(productDetailRepo.findProductDetailByProductDetailCode(productDetailCode));
    }

    @Override
    public ProductDetailDTO getById(Long id) {
        return convertToDto(productDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void create(ProductDetailDTO productDetailDTO) {
        productDetailRepo.save(convertToEntity(productDetailDTO));
    }

    @Override
    public void update(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailRepo.findById(productDetailDTO.getId()).orElseThrow(IllegalArgumentException::new);
        if (productDetail != null) {
            productDetail = convertToEntity(productDetailDTO);
            productDetailRepo.save(productDetail);
        }

    }

    @Override
    public void delete(Long id) {
        ProductDetail productDetail = productDetailRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (productDetail != null) {
            productDetailRepo.deleteById(id);
        }
    }
}
