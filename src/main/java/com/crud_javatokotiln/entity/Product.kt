package com.crud_javatokotiln.entity

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "products")
@Schema(description = "상품 엔티티")
class Product(
    id: Long? = null,

    @Schema(description = "상품 이름", example = "노트북")
    var name: String = "",

    @Schema(description = "상품 가격", example = "1200.0")
    var price: Double = 0.0
) : BaseEntity(id) {

    constructor(name: String, price: Double) : this() {
        this.name = name
        this.price = price
    }
}
