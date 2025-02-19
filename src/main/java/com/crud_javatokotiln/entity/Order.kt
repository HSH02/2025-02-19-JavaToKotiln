package com.crud_javatokotiln.entity;

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table(name = "orders")
@Schema(description = "주문 엔티티")
class Order() : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "주문한 사용자")
    lateinit var user: User

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Schema(description = "주문한 상품")
    lateinit var product: Product

    @Schema(description = "상품 수량", example = "2")
    var quantity: Int = 0

    constructor(user: User, product: Product, quantity: Int) : this() {
        this.user = user
        this.product = product
        this.quantity = quantity
    }

    constructor(id: Long, user: User, product: Product, quantity: Int) : this() {
        this.id = id
        this.user = user
        this.product = product
        this.quantity = quantity
    }

}
