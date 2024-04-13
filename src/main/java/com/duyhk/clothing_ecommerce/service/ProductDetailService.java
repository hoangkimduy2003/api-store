package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDetailDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;

import java.util.List;

public interface ProductDetailService {
    ProductDetail convertToEntity(ProductDetailDTO productDetailDTO);

    ProductDetailDTO convertToDto(ProductDetail productDetail);

    List<ProductDetailDTO> getAll();

    PageDTO<List<ProductDetailDTO>> getByIdSp(PageRequestDTO pageRequestDTO, Long id);

    PageDTO<List<ProductDetailDTO>> getByIdSpAd(PageRequestDTO pageRequestDTO, Long id);
    PageDTO<List<ProductDetailDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);

    PageDTO<List<ProductDetailDTO>> search(SearchProductDTO searchProductDTO);

    ProductDetailDTO findProductDetailByProductDetailCode(String productDetailCode);
    ProductDetailDTO getById(Long id);

    String create(ProductDetailDTO productDetailDTO);

    void update(ProductDetailDTO productDetailDTO);

    void delete(Long id);

    List<ProductDetailDTO> searchByColorName(String nameColor, Long idProduct);

    ProductDetail searchBySizeAndColor(Long idProduct, Long idColor,Long idSize);

    void changeStatus(Long id , Integer status);
}
