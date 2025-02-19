package com.crud_javatokotiln.entity;

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "products")
@Schema(description = "상품 엔티티")
class Product() : BaseEntity() {

    @Schema(description = "상품 이름", example = "노트북")
    lateinit var name: String

    @Schema(description = "상품 가격", example = "1200.0")
    var price: Double = 0.0

    constructor(name: String, price: Double) : this() {
        this.name = name
        this.price = price
    }

    constructor(id: Long, name: String, price: Double) : this() {
        this.id = id
        this.name = name
        this.price = price
    }
}
