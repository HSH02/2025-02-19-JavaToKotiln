package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.ProductDto;
import com.crud_javatokotiln.entity.Product;
import com.crud_javatokotiln.global.ApiResponse;
import com.crud_javatokotiln.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "모든 상품 조회", description = "등록된 모든 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    @Operation(summary = "특정 상품 조회", description = "ID에 해당하는 상품을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.create(productDto);
        return new ResponseEntity<>(ApiResponse.created(product), HttpStatus.CREATED);
    }

    @Operation(summary = "상품 업데이트", description = "ID에 해당하는 상품 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = productService.update(id, productDto);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    @Operation(summary = "상품 삭제", description = "ID에 해당하는 상품을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<>(ApiResponse.ok(null), HttpStatus.NO_CONTENT);
    }
}
