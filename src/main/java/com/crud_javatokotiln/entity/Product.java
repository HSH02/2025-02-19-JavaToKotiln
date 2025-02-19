package com.crud_javatokotiln.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "상품 엔티티")
public class Product extends BaseEntity {

    @Schema(description = "상품 이름", example = "노트북")
    private String name;

    @Schema(description = "상품 가격", example = "1200.0")
    private Double price;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, Double price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}
