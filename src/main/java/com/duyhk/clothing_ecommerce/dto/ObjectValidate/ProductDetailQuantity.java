package com.duyhk.clothing_ecommerce.dto.ObjectValidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailQuantity {
    private Long id;
    private String size;
    private Long quantity;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductDetailQuantity other = (ProductDetailQuantity) obj;
        return Objects.equals(size, other.size) && Objects.equals(quantity, other.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, quantity);
    }
}
