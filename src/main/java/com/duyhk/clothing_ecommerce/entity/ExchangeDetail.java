package com.duyhk.clothing_ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchange_detail")
public class ExchangeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exchange_id")
    private Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private  ProductDetail productDetail;
}
