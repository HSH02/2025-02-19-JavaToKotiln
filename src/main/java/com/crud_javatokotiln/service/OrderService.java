package com.crud_javatokotiln.service;


import com.crud_javatokotiln.dto.OrderDto;
import com.crud_javatokotiln.entity.Order;
import com.crud_javatokotiln.entity.Product;
import com.crud_javatokotiln.entity.User;
import com.crud_javatokotiln.exception.NotFoundException;
import com.crud_javatokotiln.repository.OrderRepository;
import com.crud_javatokotiln.repository.ProductRepository;
import com.crud_javatokotiln.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }

    @Transactional
    public Order create(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + orderDto.getUserId()));
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + orderDto.getProductId()));
        Order order = new Order(user, product, orderDto.getQuantity());
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(Long id, OrderDto orderDto) {
        Order order = findById(id);
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + orderDto.getUserId()));
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + orderDto.getProductId()));
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(orderDto.getQuantity());
        return orderRepository.save(order);
    }

    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // 추가 기능: 주문 총액 계산 (수량 * 상품 가격)
    public double calculateTotal(Long orderId) {
        Order order = findById(orderId);
        return order.getQuantity() * order.getProduct().getPrice();
    }
}
