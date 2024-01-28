package com.duyhk.clothing_ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO implements Serializable {
    private Long id;
    private Long totalProduct;
    private Double totalMoney;
    private UserDTO user;
    private List<CartDetailDTO> cartDetails;
}
