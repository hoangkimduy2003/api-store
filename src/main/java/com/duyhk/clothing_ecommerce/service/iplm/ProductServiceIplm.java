package com.duyhk.clothing_ecommerce.service.iplm;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.entity.Product;
import com.duyhk.clothing_ecommerce.entity.ProductDetail;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.reponsitory.ProductDetailReponsitory;
import com.duyhk.clothing_ecommerce.reponsitory.ProductReponsitory;
import com.duyhk.clothing_ecommerce.service.ProductService;
import com.duyhk.clothing_ecommerce.util.BarcodeService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceIplm implements ProductService {
    @Autowired
    private ProductReponsitory productRepo;

    @Autowired
    private ProductDetailReponsitory productDetailRepo;

    @Override
    public Product convertToEntity(ProductDTO productDTO) {
        return new ModelMapper().map(productDTO, Product.class);
    }

    @Override
    public ProductDTO convertToDto(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepo.findAll().stream().map(u -> convertToDto(u)).collect(Collectors.toList());
    }

    @Override
    public PageDTO<List<ProductDTO>> getByPageRequest(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 5 : pageRequestDTO.getSize());
        Page<Product> pageEntity = productRepo.findAll(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        listDto.stream().forEach(p -> {
            List<ProductDetailDTO> listDetailDto =
                    productDetailRepo.findByProduct(p.getId())
                            .stream().map(detail -> new ModelMapper().map(detail, ProductDetailDTO.class))
                            .collect(Collectors.toList());
            p.setProductDetails(listDetailDto);
        });
        return PageDTO.<List<ProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<ProductDTO>> getByBestSeller(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 4 : pageRequestDTO.getSize());
        Page<Product> pageEntity = productRepo.getByBestSeller(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        listDto.stream().forEach(p -> {
            List<ProductDetailDTO> listDetailDto =
                    productDetailRepo.findByProduct(p.getId())
                            .stream().map(detail -> new ModelMapper().map(detail, ProductDetailDTO.class))
                            .collect(Collectors.toList());
            p.setProductDetails(listDetailDto);
        });
        return PageDTO.<List<ProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<ProductDTO>> getByNew(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setPage(pageRequestDTO.getPage() == null ? 0 : pageRequestDTO.getPage());
        pageRequestDTO.setSize(pageRequestDTO.getSize() == null ? 4 : pageRequestDTO.getSize());
        Page<Product> pageEntity = productRepo.getByNew(
                PageRequest.of(
                        pageRequestDTO.getPage(),
                        pageRequestDTO.getSize()));
        List<ProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        listDto.stream().forEach(p -> {
            List<ProductDetailDTO> listDetailDto =
                    productDetailRepo.findByProduct(p.getId())
                            .stream().map(detail -> new ModelMapper().map(detail, ProductDetailDTO.class))
                            .collect(Collectors.toList());
            p.setProductDetails(listDetailDto);
        });
        return PageDTO.<List<ProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public ProductDTO getById(Long id) {
        return convertToDto(productRepo.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public void addProducts(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            // Skip header row if necessary
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
//                YourEntity entity = createEntityFromRow(row);
//                repository.save(entity);
            }

            workbook.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @Override
    public void create(ProductDTO productDTO) {
        if (validate(productDTO)) {
            productDTO = preCreate(productDTO);
            Product product = convertToEntity(productDTO);
            Product p = productRepo.save(product);

        }
    }

    @Override
    public void update(ProductDTO productDTO) {
        if (validate(productDTO)) {
            Product product = productRepo.findById(productDTO.getId()).orElseThrow(IllegalArgumentException::new);
            productDTO.setImages(productDTO.getImages() == null ? product.getImages() : productDTO.getImages());
            Product p = null;
            if (product != null) {
                productDTO.setTotalQuantity(product.getTotalQuantity());
                productDTO.setTotalQuantitySold(product.getTotalQuantitySold());
                productDTO.setStatus(product.getStatus());
                productDTO.setPriceSale(productDTO.getPrice());
                product = convertToEntity(productDTO);
                p = productRepo.save(product);
            }
            List<ProductDetail> productDetails = productDetailRepo.findByProduct(product.getId());
            Product finalProduct = p;
            productDetails.stream().forEach(pd -> {
                pd.setPrice(finalProduct.getPrice());
                pd.setPriceSale(finalProduct.getPriceSale());
                pd.setName(finalProduct.getName());
                pd.setProduct(finalProduct);
            });
            productDetailRepo.saveAll(productDetails);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepo.findById(id).orElseThrow(IllegalArgumentException::new);
        if (product != null) {
            productDetailRepo.deleteByProductId(id);
            productRepo.deleteById(id);
        }
    }

    @Override
    public PageDTO<List<ProductDTO>> search(SearchProductDTO searchProductDTO) {
        searchProductDTO.setPage(searchProductDTO.getPage() == null ? 0 : searchProductDTO.getPage());
        searchProductDTO.setSize(searchProductDTO.getSize() == null ? 5 : searchProductDTO.getSize());
        Page<Product> pageEntity = productRepo.search(
                "%" + (searchProductDTO.getName() != null ? searchProductDTO.getName() : "") + "%",
                searchProductDTO.getBrandId(),
                searchProductDTO.getStatus(),
                searchProductDTO.getCategoryId(),
                PageRequest.of(
                        searchProductDTO.getPage(),
                        searchProductDTO.getSize()));
        List<ProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<ProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<List<ProductDTO>> searchOnline(SearchProductDTO searchProductDTO) {
        searchProductDTO.setPage(searchProductDTO.getPage() == null ? 0 : searchProductDTO.getPage());
        searchProductDTO.setSize(searchProductDTO.getSize() == null ? 5 : searchProductDTO.getSize());
        searchProductDTO.setOrder(("no".equals(searchProductDTO.getOrder()) || searchProductDTO.getOrder() == null) ? "null" : searchProductDTO.getOrder());
        Page<Product> pageEntity = null;
        if (searchProductDTO.getOrder().contains("desc")) {
            pageEntity = productRepo.searchOnlineDescending(
                    "%" + (searchProductDTO.getName() != null ? searchProductDTO.getName() : "") + "%",
                    searchProductDTO.getBrandId(),
                    searchProductDTO.getStatus(),
                    searchProductDTO.getCategoryId(),
                    searchProductDTO.getSizeId(),
                    searchProductDTO.getColorId(),
                    searchProductDTO.getOrder(),
                    PageRequest.of(
                            searchProductDTO.getPage(),
                            searchProductDTO.getSize()));
        }else {
            pageEntity = productRepo.searchOnlineUpDescending(
                    "%" + (searchProductDTO.getName() != null ? searchProductDTO.getName() : "") + "%",
                    searchProductDTO.getBrandId(),
                    searchProductDTO.getStatus(),
                    searchProductDTO.getCategoryId(),
                    searchProductDTO.getSizeId(),
                    searchProductDTO.getColorId(),
                    searchProductDTO.getOrder(),
                    PageRequest.of(
                            searchProductDTO.getPage(),
                            searchProductDTO.getSize()));
        }
        List<ProductDTO> listDto = pageEntity.get().map(a -> convertToDto(a)).collect(Collectors.toList());
        return PageDTO.<List<ProductDTO>>builder()
                .data(listDto)
                .totalElements(pageEntity.getTotalElements())
                .totalPages(pageEntity.getTotalPages())
                .build();
    }

    public boolean validate(ProductDTO productDTO) {
        if (productDTO.getBrand().getId() == -1) {
            throw new CustomValidationException("Vui lòng chọn thương hiệu");
        }
        if (productDTO.getCategory().getId() == -1) {
            throw new CustomValidationException("Vui lòng chọn loại sản phẩm");
        }
        return true;
    }

    public ProductDTO preCreate(ProductDTO productDTO) {
        productDTO.setTotalQuantitySold(0L);
        productDTO.setTotalQuantity(0l);
        productDTO.setPriceSale(productDTO.getPrice());
        productDTO.setStatus(0);
        return productDTO;
    }
    public String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }

        return "SP" + randomString.toString();
    }
    @Override
    public void changeStatus(Long id, Integer status){
        Product product = productRepo.findById(id).orElse(null);
        if(product != null) {
            product.setStatus(status);
            List<ProductDetail> list = productDetailRepo.findByProduct(id);
            list.stream().forEach(x -> {
                x.setStatus(status);
            });
            productRepo.save(product);
            productDetailRepo.saveAll(list);
        }
    }
}
