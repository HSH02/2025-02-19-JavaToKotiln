package com.crud_javatokotiln.repository;

import com.crud_javatokotiln.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
