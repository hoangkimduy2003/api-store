package com.duyhk.clothing_ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
