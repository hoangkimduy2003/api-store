package com.duyhk.clothing_ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_product")
public class ReviewProduct extends TimeAuditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;

    private String description;

    @ManyToOne
    @JoinColumn(name = "bill_detail_id")
    private BillDetail billDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
