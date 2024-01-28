package com.duyhk.clothing_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalProduct;
    private Double totalMoney;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Cart(Long totalProduct, Double totalMoney, Users user) {
        this.totalProduct = totalProduct;
        this.totalMoney = totalMoney;
        this.user = user;
    }

}
