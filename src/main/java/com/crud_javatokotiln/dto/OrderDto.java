package com.crud_javatokotiln.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
}
