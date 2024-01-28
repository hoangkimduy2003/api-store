package com.duyhk.clothing_ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO extends TimeAuditableDTO implements Serializable {
    private Long id;
    private String name;
    private Double promotionalLevel;
    private Double maximumPromotion;
    private Date start;
    private Date end;
    private Integer status;
    private List<PromotionDetailDTO> promotionDetails;
}
