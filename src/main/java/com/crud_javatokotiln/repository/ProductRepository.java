package com.crud_javatokotiln.repository;


import com.crud_javatokotiln.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
