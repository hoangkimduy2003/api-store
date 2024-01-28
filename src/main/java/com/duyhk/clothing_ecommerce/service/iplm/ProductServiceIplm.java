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
            List<ProductDetail> list = productDTO.getProductDetails().stream().map(u -> new ModelMapper().map(u, ProductDetail.class)).collect(Collectors.toList());
            list.stream().forEach(x -> {
                String productDetailCode = generateRandomString();
                BarcodeService.generateAndSaveBarcode(productDetailCode);
                x.setProductDetailCode(productDetailCode);
                x.setProduct(p);
            });
            productDetailRepo.saveAll(list);
        }
    }

    @Override
    public void update(ProductDTO productDTO) {
        if (validate(productDTO)) {
            Product product = productRepo.findById(productDTO.getId()).orElseThrow(IllegalArgumentException::new);
            productDTO.setImages(productDTO.getImages() == null ? product.getImages() : productDTO.getImages());
            if (product != null) {
                productDTO = preUpdate(productDTO);
                product = convertToEntity(productDTO);
                Product p = productRepo.save(product);
                List<ProductDetail> list = productDTO.getProductDetails().stream().map(u -> new ModelMapper().map(u, ProductDetail.class)).collect(Collectors.toList());
                list.stream().forEach(x -> x.setProduct(p));
                productDetailRepo.saveAll(list);
            }
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

    public boolean validate(ProductDTO productDTO) {
        if (productDTO.getBrand().getId() == -1) {
            throw new CustomValidationException("Vui lòng chọn thương hiệu");
        }
        if (productDTO.getCategory().getId() == -1) {
            throw new CustomValidationException("Vui lòng chọn loại sản phẩm");
        }
        if (productDTO.getProductDetails().size() == 0) {
            throw new CustomValidationException("Vui lòng thêm thông tin chi tiết sản phẩm");
        }
        for (ProductDetailDTO x : productDTO.getProductDetails()) {
            if (x.getColor().getId() == -1) {
                throw new CustomValidationException("Vui lòng chọn màu sắc");
            }
            if (x.getSize().getId() == -1) {
                throw new CustomValidationException("Vui lòng chọn kích cỡ");
            }
            if (x.getQuantity() < 1) {
                throw new CustomValidationException("Vui lòng nhập số lượng");
            }
        }
        return true;
    }

    public ProductDTO preCreate(ProductDTO productDTO) {
        productDTO.setTotalQuantitySold(0L);
        productDTO.setTotalQuantity(
                productDTO.getProductDetails()
                        .stream()
                        .map(ProductDetailDTO::getQuantity)
                        .reduce(0L, Long::sum));
        productDTO.setPriceSale(productDTO.getPrice());
        for (ProductDetailDTO x : productDTO.getProductDetails()) {
            x.setName(productDTO.getName());
            x.setPrice(productDTO.getPrice());
            x.setPriceSale(productDTO.getPriceSale());
            x.setQuantitySold(0L);
            x.setAveragedReview(0.0);
            x.setStatus(productDTO.getStatus());
        }
        return productDTO;
    }

    public ProductDTO preUpdate(ProductDTO productDTO) {
        productDTO.setTotalQuantitySold(0L);
        productDTO.setTotalQuantity(
                productDTO.getProductDetails()
                        .stream()
                        .map(ProductDetailDTO::getQuantity)
                        .reduce(0L, Long::sum));
        productDTO.setTotalQuantitySold(
                productDTO.getProductDetails()
                        .stream()
                        .map(ProductDetailDTO::getQuantitySold)
                        .reduce(0L, Long::sum));

        productDTO.setPriceSale(productDTO.getPrice());

        for (ProductDetailDTO x : productDTO.getProductDetails()) {
            x.setName(productDTO.getName());
            x.setPrice(productDTO.getPrice());
            x.setPriceSale(productDTO.getPriceSale());
            x.setStatus(productDTO.getStatus());
        }
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
}
