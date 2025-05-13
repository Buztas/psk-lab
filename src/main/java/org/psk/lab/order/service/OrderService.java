package org.psk.lab.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.order.data.dto.*;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.model.OrderItem;
import org.psk.lab.order.data.model.StatusType;
import org.psk.lab.order.data.repository.OrderItemRepository;
import org.psk.lab.order.data.repository.OrderRepository;
import org.psk.lab.order.exception.InvalidStatusValueException;
import org.psk.lab.order.exception.OptimisticLockingConflictException;
import org.psk.lab.order.exception.OrderNotFoundException;
import org.psk.lab.order.mapper.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    // private final MenuItemRepository menuItemRepository; // TODO: Inject when MenuItem component is integrated
    // private final UserRepository userRepository; // TODO: Inject when User component is integrated
    // private  final ItemVariationRepository itemVariationRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderViewDto createOrder(OrderCreateRequestDto requestDto) {
        Order order = new Order();
        // TODO: set user
        order.setOrderDate(LocalDateTime.now());
        order.setPickupTime(null);
        order.setStatus(StatusType.PENDING);
        List<OrderItem> processedOrderItems = new ArrayList<>();
        BigDecimal totalOrderAmount = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : requestDto.getItems()) {
            //MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            //orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());
            //BigDecimal menuItemPrice = menuItem.getPrice();
            //BigDecimal lineItemTotalPrice = menuItemPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            //orderItem.setTotalPrice(lineItemTotalPrice);

            //mock value for testing
            BigDecimal mockMenuItemPrice = new BigDecimal("5.00");
            BigDecimal lineItemTotalPrice = mockMenuItemPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setTotalPrice(lineItemTotalPrice);

            processedOrderItems.add(orderItem);
            totalOrderAmount = totalOrderAmount.add(lineItemTotalPrice);
        }

        order.setTotalAmount(totalOrderAmount);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : processedOrderItems) {
            item.setOrder(savedOrder);
        }
        orderItemRepository.saveAll(processedOrderItems);

        return this.orderMapper.toOrderViewDto(savedOrder);
    }

    @Transactional
    public Optional<OrderViewDto> getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toOrderViewDto);
    }

    @Transactional
    public Page<OrderSummaryDto> getAllOrders(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(orderMapper::toOrderSummaryDto);
    }

    @Transactional
    public OrderViewDto updateOrderStatus(UUID orderId, OrderStatusUpdateRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!order.getVersion().equals(requestDto.getVersion())) {
            throw new OptimisticLockingConflictException(
                    "Order update failed due to a version mismatch. Expected version: " +
                    requestDto.getVersion() + ", but current version is: " + order.getVersion() + "." +
                    " Please refresh and try again."
            );
        }

        StatusType newStatusEnum;
        try {
            newStatusEnum = StatusType.valueOf(requestDto.getNewStatus().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusValueException("Invalid status value provided: '" + requestDto.getNewStatus() +
                    "'. Valid statuses are: " + List.of(StatusType.values()), e);
        }
        //TODO: isValidStatusTransition ?
        order.setStatus(newStatusEnum);
        Order savedOrder = orderRepository.save(order);

        return this.orderMapper.toOrderViewDto(savedOrder);
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }
}
