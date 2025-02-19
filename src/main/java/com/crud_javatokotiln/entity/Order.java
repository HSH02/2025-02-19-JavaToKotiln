package com.crud_javatokotiln.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "주문 엔티티")
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Schema(description = "주문한 사용자")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Schema(description = "주문한 상품")
    private Product product;

    @Schema(description = "상품 수량", example = "2")
    private int quantity;

    public Order(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public Order(Long id, User user, Product product, int quantity) {
        super(id);
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

}
