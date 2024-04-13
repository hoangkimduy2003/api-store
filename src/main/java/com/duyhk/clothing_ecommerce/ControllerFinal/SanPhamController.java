package com.duyhk.clothing_ecommerce.ControllerFinal;

import com.duyhk.clothing_ecommerce.dto.PageRequestDTO;
import com.duyhk.clothing_ecommerce.dto.ProductDTO;
import com.duyhk.clothing_ecommerce.dto.search.SearchProductDTO;
import com.duyhk.clothing_ecommerce.exception.CustomValidationException;
import com.duyhk.clothing_ecommerce.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/san-pham")
public class SanPhamController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;



    @GetMapping
    public String home(@ModelAttribute SearchProductDTO searchProductDTO, Model model){
        model.addAttribute("list", productService.search(searchProductDTO));
        model.addAttribute("brands", brandService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "SanPham/SanPham";
    }

    @GetMapping("/changeStatus/{id}/{status}")
    @ResponseBody
    public void changeStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status){
            productService.changeStatus(id,status);
    }

    @PostMapping("/action")
    public String action(@ModelAttribute ProductDTO productDTO) throws IOException {
        if(productDTO.getId() == null){
            List<String> images = new ArrayList<>();
            if (productDTO.getFilesUpload() != null && !productDTO.getFilesUpload().get(0).getOriginalFilename().equalsIgnoreCase("")) {
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
        }else{
            List<String> images = null;
            if (productDTO.getFilesUpload() != null && !productDTO.getFilesUpload().get(0).getOriginalFilename().equalsIgnoreCase("")) {
                images = new ArrayList<>();
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
            }
            productDTO.setImages(images);
            productService.update(productDTO);
        }

        return "redirect:/san-pham";
    }

    @GetMapping("/img")
    @ResponseBody
    public void showImages(@RequestParam("fileName") String fileName,
                           HttpServletResponse resp) throws IOException {
        File file = new File("D:/images/" + fileName);
        String contentType = getContentTypeByFileExtension(fileName);
        resp.setContentType(contentType);
        Files.copy(file.toPath(), resp.getOutputStream());
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
}
