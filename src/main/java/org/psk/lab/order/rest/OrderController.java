package org.psk.lab.order.rest;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psk.lab.order.data.dto.*;
import org.psk.lab.order.service.OrderService;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Endpoints regarding order management")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<OrderViewDto> createOrder(@Valid @RequestBody OrderCreateRequestDto requestDto, Principal principal) {
        String username = principal.getName();
        MyUser authenticatedUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        UUID authenticatedUserId = authenticatedUser.getUuid();
        OrderViewDto createdOrderDto = orderService.createOrder(authenticatedUserId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDto);
    }

    @PostMapping("/admin/orders")
    public ResponseEntity<OrderViewDto> createOrderAdmin(@Valid @RequestBody AdminOrderCreateRequestDto adminRequestDto) {
        OrderCreateRequestDto itemsDto = new OrderCreateRequestDto();
        itemsDto.setItems(adminRequestDto.getItems());
        OrderViewDto createdOrderDto = orderService.createOrder(adminRequestDto.getUserId(), itemsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderViewDto> getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<OrderSummaryDto>> getAllOrders(
            @PageableDefault(size = 20, sort = "orderDate") Pageable pageable
    ) {
        Page<OrderSummaryDto> orderPage = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orderPage);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderViewDto> updateOrderStatus (
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto) {
        OrderViewDto updatedOrderDto = orderService.updateOrderStatus(orderId, requestDto);
        return ResponseEntity.ok(updatedOrderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
