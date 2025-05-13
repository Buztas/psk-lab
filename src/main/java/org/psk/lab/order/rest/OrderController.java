package org.psk.lab.order.rest;


import jakarta.validation.Valid;
import org.psk.lab.order.data.dto.OrderCreateRequestDto;
import org.psk.lab.order.data.dto.OrderStatusUpdateRequestDto;
import org.psk.lab.order.data.dto.OrderViewDto;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto) {
        try {
            OrderViewDto createdOrder = orderService.createOrder(orderCreateRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating order");
        } // catch more specific exceptions later
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderViewDto> getOrderById(@Valid @PathVariable UUID orderId) {
        Optional<OrderViewDto> orderDtoOptional = orderService.getOrderById(orderId);

        return orderDtoOptional
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus (
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto orderStatusUpdateRequestDto) {
        try {
            OrderViewDto updatedOrder = orderService.updateOrderStatus(orderId, orderStatusUpdateRequestDto);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            System.err.println("Error updating order status for order ID " + orderId + ": " + e.getMessage());

            if (e.getMessage() != null && e.getMessage().contains("Order not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } else if (e.getMessage() != null && e.getMessage().contains("version mismatch")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating order status.");
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            System.err.println("Error deleting order: " + orderId + ": " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
