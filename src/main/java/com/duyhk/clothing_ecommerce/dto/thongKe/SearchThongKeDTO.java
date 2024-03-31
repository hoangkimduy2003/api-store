package com.duyhk.clothing_ecommerce.dto.thongKe;

import lombok.Data;

@Data
public class SearchThongKeDTO {
    private String date;
    private Integer showType;
    private Integer page;
    private Integer size;

    //search bill
    private Integer billType;
    private Integer status;

    private Integer page2;
}
