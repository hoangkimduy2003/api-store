package com.duyhk.clothing_ecommerce.dto;

import com.duyhk.clothing_ecommerce.entity.Brand;
import com.duyhk.clothing_ecommerce.entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends TimeAuditableDTO implements Serializable {
    private Long id;
    @NotNull(message = "Vui lòng nhập tên sản phẩm")
    @NotEmpty(message = "Vui lòng nhập tên sản phẩm")
    @NotBlank(message = "Vui lòng nhập tên sản phẩm")
    private String name;
    @NotNull(message = "Vui lòng nhập giá nhập")
    @Min(value = 1,message = "Giá nhập phải lớn hơn 0")
    private Double importPrice;
    @NotNull(message = "Vui lòng nhập giá bán")
    @Min(value = 1,message = "Giá bán phải lớn hơn 0")
    private Double price;
    private Double priceSale;
    private Long totalQuantitySold;
    private Long totalQuantity;
    @NotNull(message = "Vui lòng nhập mô tả sản phẩm")
    @NotEmpty(message = "Vui lòng nhập mô tả sản phẩm")
    private String description;
    @Min(value = 0, message = "Vui lòng chọn trạng thái")
    private Integer status;
    private List<String> images;
    @NotNull(message = "Vui lòng chọn loại sản phẩm")
    private CategoryDTO category;
    @NotNull(message = "Vui lòng chọn thương hiệu")
    private BrandDTO brand;
    @NotNull(message = "Vui lòng thêm thông tin sản phẩm")
    private List<ProductDetailDTO> productDetails;
    @JsonIgnore
    private List<MultipartFile> filesUpload;
}
