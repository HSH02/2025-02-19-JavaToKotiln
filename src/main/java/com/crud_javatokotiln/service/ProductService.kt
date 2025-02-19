package com.crud_javatokotiln.service

import com.crud_javatokotiln.dto.ProductDto
import com.crud_javatokotiln.entity.Product
import com.crud_javatokotiln.exception.NotFoundException
import com.crud_javatokotiln.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
open class ProductService(
    private val productRepository: ProductRepository
) {

    fun findAll(): List<Product> = productRepository.findAll()

    fun findById(id: Long): Product =
        productRepository.findById(id)
            .orElseThrow { NotFoundException("Product not found with id: $id") }

    @Transactional
    open fun create(productDto: ProductDto): Product {
        val name = productDto.name ?: throw IllegalArgumentException("Name cannot be null")
        val price = productDto.price ?: throw IllegalArgumentException("Price cannot be null")
        val product = Product(name, price)
        return productRepository.save(product)
    }

    @Transactional
    open fun update(id: Long, productDto: ProductDto): Product {
        val product = findById(id)
        product.name = productDto.name ?: product.name
        product.price = productDto.price ?: product.price
        return productRepository.save(product)
    }

    @Transactional
    open fun deleteById(id: Long) {
        if (!productRepository.existsById(id)) {
            throw NotFoundException("Product not found with id: $id")
        }
        productRepository.deleteById(id)
    }
}
