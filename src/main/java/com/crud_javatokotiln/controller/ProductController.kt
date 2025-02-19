package com.crud_javatokotiln.controller

import com.crud_javatokotiln.dto.ProductDto
import com.crud_javatokotiln.entity.Product
import com.crud_javatokotiln.global.ApiResponse
import com.crud_javatokotiln.global.ApiResponse.Companion.created
import com.crud_javatokotiln.global.ApiResponse.Companion.ok
import com.crud_javatokotiln.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    @Operation(summary = "모든 상품 조회", description = "등록된 모든 상품을 조회합니다.")
    fun allProducts(): ResponseEntity<ApiResponse<List<Product>>> {
        val products = productService.findAll()
        return ResponseEntity.ok(ok(products))
    }

    @Operation(summary = "특정 상품 조회", description = "ID에 해당하는 상품을 조회합니다.")
    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ApiResponse<Product>> {
        val product = productService.findById(id)
        return ResponseEntity.ok(ok(product))
    }

    @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
    @PostMapping
    fun createProduct(@RequestBody productDto: ProductDto): ResponseEntity<ApiResponse<Product>> {
        val product = productService.create(productDto)
        return ResponseEntity(created(product), HttpStatus.CREATED)
    }

    @Operation(summary = "상품 업데이트", description = "ID에 해당하는 상품 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody productDto: ProductDto
    ): ResponseEntity<ApiResponse<Product>> {
        val product = productService.update(id, productDto)
        return ResponseEntity.ok(ok(product))
    }

    @Operation(summary = "상품 삭제", description = "ID에 해당하는 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<ApiResponse<Void?>> {
        productService.deleteById(id)
        return ResponseEntity(ok(null), HttpStatus.NO_CONTENT)
    }
}
