package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.OrderDto;
import com.crud_javatokotiln.entity.Order;
import com.crud_javatokotiln.entity.Product;
import com.crud_javatokotiln.entity.User;
import com.crud_javatokotiln.service.OrderService;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllOrders() throws Exception {
        User user = new User(1L, "홍길동", "hong@example.com");
        Product product = new Product(1L, "노트북", 1500.0);
        Order order = new Order(1L, user, product, 2);
        List<Order> orders = List.of(order);
        Mockito.when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].quantity").value(2))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void getOrder() throws Exception {
        User user = new User(1L, "홍길동", "hong@example.com");
        Product product = new Product(1L, "노트북", 1500.0);
        Order order = new Order(1L, user, product, 2);
        Mockito.when(orderService.findById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.quantity").value(2))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void createOrder() throws Exception {
        OrderDto orderDto = new OrderDto(null, 1L, 1L, 2);
        User user = new User(1L, "홍길동", "hong@example.com");
        Product product = new Product(1L, "노트북", 1500.0);
        Order createdOrder = new Order(1L, user, product, 2);
        Mockito.when(orderService.create(any(OrderDto.class))).thenReturn(createdOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.quantity").value(2))
                .andExpect(jsonPath("$.message").value("Created"));
    }

    @Test
    void updateOrder() throws Exception {
        OrderDto orderDto = new OrderDto(null, 1L, 1L, 3);
        User user = new User(1L, "홍길동", "hong@example.com");
        Product product = new Product(1L, "노트북", 1500.0);
        Order updatedOrder = new Order(1L, user, product, 3);
        Mockito.when(orderService.update(eq(1L), any(OrderDto.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.quantity").value(3))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void deleteOrder() throws Exception {
        Mockito.doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void calculateTotal() throws Exception {
        Mockito.when(orderService.calculateTotal(1L)).thenReturn(3000.0);

        mockMvc.perform(get("/orders/1/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.orderId").value(1))
                .andExpect(jsonPath("$.data.total").value(3000.0))
                .andExpect(jsonPath("$.message").value("Success"));
    }
}

