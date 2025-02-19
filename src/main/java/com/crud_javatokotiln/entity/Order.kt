package com.crud_javatokotiln.entity;

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table(name = "orders")
@Schema(description = "주문 엔티티")
class Order(
    id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "주문한 사용자")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Schema(description = "주문한 상품")
    var product: Product? = null,

    @Schema(description = "상품 수량", example = "2")
    var quantity: Int = 0
) : BaseEntity(id) {
    constructor(user: User, product: Product, quantity: Int)
            : this(null, user, product, quantity)
}
