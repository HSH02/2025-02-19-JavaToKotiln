package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.OrderDto;
import com.crud_javatokotiln.entity.Order;
import com.crud_javatokotiln.global.ApiResponse;
import com.crud_javatokotiln.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "모든 주문 조회", description = "등록된 모든 주문을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(orders));
    }

    @Operation(summary = "특정 주문 조회", description = "ID에 해당하는 주문을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrder(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(order));
    }

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.create(orderDto);
        return new ResponseEntity<>(ApiResponse.created(order), HttpStatus.CREATED);
    }

    @Operation(summary = "주문 업데이트", description = "ID에 해당하는 주문 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order order = orderService.update(id, orderDto);
        return ResponseEntity.ok(ApiResponse.ok(order));
    }

    @Operation(summary = "주문 삭제", description = "ID에 해당하는 주문을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(ApiResponse.ok(null), HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "주문 총액 계산", description = "주문 ID에 해당하는 주문의 총액을 계산합니다.")
    @GetMapping("/{id}/total")
    public ResponseEntity<ApiResponse<Map<String, Object>>> calculateTotal(@PathVariable Long id) {
        double total = orderService.calculateTotal(id);
        Map<String, Object> result = Map.of("orderId", id, "total", total);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}

