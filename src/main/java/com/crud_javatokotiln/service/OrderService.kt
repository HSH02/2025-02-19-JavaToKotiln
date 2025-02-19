package com.crud_javatokotiln.service

import com.crud_javatokotiln.dto.OrderDto
import com.crud_javatokotiln.entity.Order
import com.crud_javatokotiln.exception.NotFoundException
import com.crud_javatokotiln.repository.OrderRepository
import com.crud_javatokotiln.repository.ProductRepository
import com.crud_javatokotiln.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
open class OrderService(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    fun findAll(): List<Order> = orderRepository.findAll()

    fun findById(id: Long): Order =
        orderRepository.findById(id)
            .orElseThrow { NotFoundException("Order not found with id: $id") }

    @Transactional
    open fun create(orderDto: OrderDto): Order {
        val user = userRepository.findById(orderDto.userId)
            .orElseThrow { NotFoundException("User not found with id: ${orderDto.userId}") }!!
        val product = productRepository.findById(orderDto.productId)
            .orElseThrow { NotFoundException("Product not found with id: ${orderDto.productId}") }!!
        val order = Order(user, product, orderDto.quantity)
        return orderRepository.save(order)
    }

    @Transactional
    open fun update(id: Long, orderDto: OrderDto): Order {
        val order = findById(id)
        val user = userRepository.findById(orderDto.userId)
            .orElseThrow { NotFoundException("User not found with id: ${orderDto.userId}") }
        val product = productRepository.findById(orderDto.productId)
            .orElseThrow { NotFoundException("Product not found with id: ${orderDto.productId}") }
        order.user = user
        order.product = product
        order.quantity = orderDto.quantity
        return orderRepository.save(order)
    }

    @Transactional
    open fun delete(id: Long) {
        orderRepository.delete(findById(id))
    }


    fun calculateTotal(orderId: Long): Double {
        val order = findById(orderId)
        val product = order.product ?: throw NotFoundException("Product not found for order: $orderId")
        return order.quantity * product.price
    }

}
