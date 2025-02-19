package com.crud_javatokotiln.service;


import com.crud_javatokotiln.dto.ProductDto;
import com.crud_javatokotiln.entity.Product;
import com.crud_javatokotiln.exception.NotFoundException;
import com.crud_javatokotiln.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    @Transactional
    public Product create(ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice());
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductDto productDto) {
        Product product = findById(id);
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
