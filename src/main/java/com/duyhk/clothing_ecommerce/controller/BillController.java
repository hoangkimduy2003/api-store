package com.duyhk.clothing_ecommerce.controller;

import com.duyhk.clothing_ecommerce.dto.*;
import com.duyhk.clothing_ecommerce.dto.search.SearchBillDTO;
import com.duyhk.clothing_ecommerce.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("")
    public ResponseDTO<List<BillDTO>> getAll() {
        return ResponseDTO.<List<BillDTO>>builder()
                .data(billService.getAll())
                .status(200)
                .build();
    }

    @GetMapping("/page")
    public ResponseDTO<PageDTO<List<BillDTO>>> getByPageRequest(@RequestParam(name = "page", required = false) Integer page,
                                                                @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<BillDTO>>>builder()
                .data(billService.getByPageRequest(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/sell/page")
    public ResponseDTO<PageDTO<List<BillDTO>>> getSell(@RequestParam(name = "page", required = false) Integer page,
                                                       @RequestParam(name = "size", required = false) Integer size) {
        return ResponseDTO.<PageDTO<List<BillDTO>>>builder()
                .data(billService.getSellAtStore(new PageRequestDTO(page, size)))
                .status(200)
                .build();
    }

    @GetMapping("/filter")
    public ResponseDTO<PageDTO<List<BillDTO>>> getByStatus(@RequestParam(name = "page", required = false) Integer page,
                                                           @RequestParam(name = "size", required = false) Integer size,
                                                           @RequestParam(name = "status", required = false) Integer status){
        return ResponseDTO.<PageDTO<List<BillDTO>>>builder()
                .data(billService.getByStatus(new PageRequestDTO(page, size),status))
                .status(200)
                .build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<BillDTO>>> search(@RequestBody SearchBillDTO searchBillDTO){
        return ResponseDTO.<PageDTO<List<BillDTO>>>builder()
                .data(billService.search(searchBillDTO))
                .status(200)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<BillDTO> getById(@PathVariable Long id) {
        return ResponseDTO.<BillDTO>builder()
                .status(200)
                .data(billService.getById(id))
                .build();
    }

    @PostMapping("")
    public ResponseDTO<Void> create(@RequestBody BillDTO billDTO) {
        billService.createAtStore(billDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Tạo hoá đơn thành công")
                .build();
    }

    @PutMapping("/atStore/{id}")
    public ResponseDTO<Void> atStore(@RequestBody BillDTO billDTO,
                                    @PathVariable Long id) {
        billDTO.setId(id);
        billService.updateSellAtStoreFinal(billDTO);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Thanh toán thành công")
                .build();
    }

    @PutMapping("/changeStatus/{id}/{status}")
    public ResponseDTO<Void> updateStatusById(@PathVariable("id") Long id,
                                              @PathVariable("status") Integer status){
        billService.updateStatusById(id,status);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Cập nhật thành công!")
                .build();
    }

    @PutMapping("/cancel/{id}")
    public ResponseDTO<Void> cancelBill(@PathVariable Long id) {
        billService.cancelBill(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Huỷ hoá đơn thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> deleteById(@PathVariable Long id) {
        billService.delete(id);
        return ResponseDTO.<Void>builder()
                .status(200)
                .msg("Xoá hoá đơn thành công")
                .build();
    }

}
