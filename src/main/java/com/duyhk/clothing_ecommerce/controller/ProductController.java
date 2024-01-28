package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.service.ProductService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseDTO<List<ProductDTO>> getAll() {
        return ResponseDTO.<List<ProductDTO>>builder()
                .data(productService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<ProductDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ProductDTO>>>builder()
                .data(productService.getByPageRequest(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/bestSeller")
    public ResponseDTO<PageDTO<List<ProductDTO>>> getByBestSeller(@RequestParam(name = "page", required = false) Integer page,
                                                                   @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ProductDTO>>>builder()
                .data(productService.getByBestSeller(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<ProductDTO>>> search(@RequestBody SearchProductDTO searchProductDTO) {
        return ResponseDTO.<PageDTO<List<ProductDTO>>>builder()
                .data(productService.search(searchProductDTO))
                .status(200)
                .build();
    }

    @PostMapping("/addAll")
    public  ResponseDTO<Void> addAll(@RequestParam("file") MultipartFile multipartFile){
        productService.addProducts(multipartFile);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm sản phẩm thành công")
                .build();
    }

    @GetMapping("/new")
    public ResponseDTO<PageDTO<List<ProductDTO>>> betByNew(@RequestParam(name = "page", required = false) Integer page,
                                                                  @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<ProductDTO>>>builder()
                .data(productService.getByNew(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<ProductDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<ProductDTO>builder()
                .status(200)
                .data(productService.getById(id))
                .build();
    }

    private String getContentTypeByFileExtension(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else {
            return null;
        }
    }

    @GetMapping("/img")
    public void showImages(@RequestParam("fileName") String fileName,
                           HttpServletResponse resp) throws IOException {
        File file = new File("D:/images/" + fileName);
        String contentType = getContentTypeByFileExtension(fileName);
        resp.setContentType(contentType);
        Files.copy(file.toPath(), resp.getOutputStream());
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@ModelAttribute @Valid ProductDTO productDTO) throws IOException {
        List<String> images = new ArrayList<>();
        if (productDTO.getFilesUpload() != null) {
            for (MultipartFile multipartFile : productDTO.getFilesUpload()) {
                String name = multipartFile.getOriginalFilename();
                images.add(name);
                String pathFolder = "D:/images";
                File path = new File(pathFolder);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File fileUpload = new File(pathFolder + "/" + name);
                multipartFile.transferTo(fileUpload);
            }
        } else {
            throw new CustomValidationException("Vui lòng chọn ảnh");
        }
        productDTO.setImages(images);
        productService.create(productDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thêm sản phẩm thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseDTO<Void> update(@ModelAttribute @Valid ProductDTO productDTO,
                                    @PathVariable Long id) throws IOException {
        if (productDTO.getFilesUpload() != null) {
            List<String> images = new ArrayList<>();
            for (MultipartFile multipartFile : productDTO.getFilesUpload()) {
                String name = multipartFile.getOriginalFilename();
                images.add(name);
                String pathFolder = "D:/images";
                File path = new File(pathFolder);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File fileUpload = new File(pathFolder + "/" + name);
                multipartFile.transferTo(fileUpload);
            }
            productDTO.setImages(images);
        }
        productDTO.setId(id);
        productService.update(productDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Sửa sản phẩm thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá sản phẩm thành công")
                .build();
    }
}
