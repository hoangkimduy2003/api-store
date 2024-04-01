package com.duyhk.clothing_ecommerce.service;

import com.duyhk.clothing_ecommerce.dto.PageDTO;
import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDTO;
import com.duyhk.clothing_ecommerce.dto.PromotionDetailDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product convertToEntity(ProductDTO productDTO);

    ProductDTO convertToDto(Product product);

    List<ProductDTO> getAll();

    PageDTO<List<ProductDTO>> getByPageRequest(PageRequestDTO pageRequestDTO);
    PageDTO<List<ProductDTO>> getByBestSeller(PageRequestDTO pageRequestDTO);
    PageDTO<List<ProductDTO>> getByNew(PageRequestDTO pageRequestDTO);

    ProductDTO getById(Long id);

    void addProducts(MultipartFile multipartFile);
    void create(ProductDTO productDTO);

    void update(ProductDTO productDTO);

    void delete(Long id);

    PageDTO<List<ProductDTO>> search(SearchProductDTO searchProductDTO);

    Long totalProductSold();

    Long totalProductExist();
}
