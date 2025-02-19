package com.crud_javatokotiln.dto


data class OrderDto(
    var id: Long? = null,
    var userId: Long? = null,
    var productId: Long? = null,
    var quantity: Int = 0
)
