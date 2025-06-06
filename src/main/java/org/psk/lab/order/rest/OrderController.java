package org.psk.lab.order.rest;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psk.lab.notification.domain.services.NotificationService;
import org.psk.lab.notification.helper.enums.NotificationType;
import org.psk.lab.order.data.dto.*;
import org.psk.lab.order.service.OrderService;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.exception.UserNotFoundException;
import org.psk.lab.util.interceptor.LogInvocations;
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
@LogInvocations
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<OrderViewDto> createOrder(@Valid @RequestBody OrderCreateRequestDto requestDto) {
        OrderViewDto createdOrderDto = orderService.createOrder(requestDto);
        var notification = notificationService.createNotification(createdOrderDto.getOrderId(), NotificationType.EMAIL, "Your order has been FULFILLED!\n");
        notificationService.send(notification);
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
            @PageableDefault(size = 20, sort = "orderDate,desc") Pageable pageable
    ) {
        Page<OrderSummaryDto> orderPage = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orderPage);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderSummaryDto>> getOrdersByUserId(
            @PathVariable UUID userId,
            @PageableDefault(size = 10, sort = "orderDate,desc") Pageable pageable
    ) {
        Page<OrderSummaryDto> userOrdersPage = orderService.getOrdersByUserId(userId, pageable);
        return ResponseEntity.ok(userOrdersPage);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<Page<OrderSummaryDto>> getMyOrders(
            Principal principal,
            @PageableDefault(size = 10, sort = "orderDate,desc") Pageable pageable
    ) {
        String username = principal.getName();
        MyUser authenticatedUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        UUID authenticatedUserId = authenticatedUser.getUuid();

        Page<OrderSummaryDto> myOrdersPage = orderService.getOrdersByUserId(authenticatedUserId, pageable);
        return ResponseEntity.ok(myOrdersPage);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderViewDto> updateOrderStatus (
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto) {
        OrderViewDto updatedOrderDto = orderService.updateOrderStatus(orderId, requestDto);
        var notification = notificationService.createNotification(updatedOrderDto.getOrderId(), NotificationType.EMAIL, "Your order has been UPDATED!\n");
        notificationService.send(notification);
        return ResponseEntity.ok(updatedOrderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        var notification = notificationService.createNotification(orderId, NotificationType.EMAIL, "Your order has been DELETED!\n");
        notificationService.send(notification);
        return ResponseEntity.noContent().build();
    }
}
