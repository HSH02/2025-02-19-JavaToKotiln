package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.ProductDto;
import com.crud_javatokotiln.entity.Product;
import com.crud_javatokotiln.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception {
        // ID 포함 생성자 사용
        List<Product> products = List.of(new Product(1L, "노트북", 1500.0));
        Mockito.when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("노트북"))
                .andExpect(jsonPath("$.data[0].price").value(1500.0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void getProduct() throws Exception {
        // ID 포함 생성자 사용
        Product product = new Product(1L, "노트북", 1500.0);
        Mockito.when(productService.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("노트북"))
                .andExpect(jsonPath("$.data.price").value(1500.0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void createProduct() throws Exception {
        ProductDto productDto = new ProductDto(null, "노트북", 1500.0);
        // 생성 후 반환되는 객체에 ID 포함
        Product createdProduct = new Product(1L, "노트북", 1500.0);
        Mockito.when(productService.create(any(ProductDto.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("노트북"))
                .andExpect(jsonPath("$.message").value("Created"));
    }

    @Test
    void updateProduct() throws Exception {
        ProductDto productDto = new ProductDto(null, "노트북 수정", 1600.0);
        // 업데이트 결과에도 ID 포함
        Product updatedProduct = new Product(1L, "노트북 수정", 1600.0);
        Mockito.when(productService.update(eq(1L), any(ProductDto.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.name").value("노트북 수정"))
                .andExpect(jsonPath("$.data.price").value(1600.0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void deleteProduct() throws Exception {
        Mockito.doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
