package org.psk.lab.order.service;

import jakarta.transaction.Transactional;
import org.psk.lab.order.data.dto.*;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.model.OrderItem;
import org.psk.lab.order.data.model.StatusType;
import org.psk.lab.order.data.repository.OrderItemRepository;
import org.psk.lab.order.data.repository.OrderRepository;
import org.psk.lab.order.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    // private final MenuItemRepository menuItemRepository; // TODO: Inject when MenuItem component is integrated
    // private final UserRepository userRepository; // TODO: Inject when User component is integrated


    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository
                        // MenuItemRepository menuItemRepository, // TODO
                        // UserRepository userRepository // TODO
                        ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        // this.menuItemRepository = menuItemRepository;
        // this.userRepository = userRepository;
    }

    @Transactional
    public Order createOrder(OrderCreateRequestDto requestDto) {
        if (requestDto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Order order = new Order();

        // TODO: set user
        order.setOrderDate(LocalDateTime.now());
        order.setPickupTime(null);
        order.setStatus(StatusType.PENDING);
        List<OrderItem> processedOrderItems = new ArrayList<>();
        BigDecimal totalOrderAmount = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : requestDto.getItems()) {
            if (itemDto.getMenuItemId() == null) {
                throw new IllegalArgumentException("MenuItem ID cannot be null for an item.");
            }
            if (itemDto.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity for an item must be above zero: " + itemDto.getMenuItemId());
            }

            //MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId());

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            //orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());

            //BigDecimal menuItemPrice = menuItem.getPrice();
            //BigDecimal lineItemTotalPrice = menuItemPrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            //orderItem.setTotalPrice(lineItemTotalPrice);

            //delete these 3 lines after testing
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

        return savedOrder;
    }

    @Transactional
    public Optional<OrderViewDto> getOrderById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }

        return orderRepository.findById(orderId).map(this::mapOrderToOrderViewDto);
    }

    @Transactional
    public OrderViewDto updateOrderStatus(UUID orderId, OrderStatusUpdateRequestDto requestDto) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null.");
        }
        if (requestDto == null || requestDto.getNewStatus() == null || requestDto.getVersion() == null) {
            throw new IllegalArgumentException("Request dto, new status, version cannot be null.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!order.getVersion().equals(requestDto.getVersion())) {
            throw new RuntimeException(
                    "Order update failed due to a version mismatch. Expected version: " +
                    requestDto.getVersion() + ", but current version is: " + order.getVersion() + "." +
                    " Please refresh and try again."
            );
        }

        StatusType newStatusEnum;
        try {
            newStatusEnum = StatusType.valueOf(requestDto.getNewStatus().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value provided: '" + requestDto.getNewStatus() +
                    "'. Valid statuses are: " + List.of(StatusType.values()), e);
        }

        //TODO: isValidStatusTransition

        order.setStatus(newStatusEnum);

        Order savedOrder = orderRepository.save(order);

        return mapOrderToOrderViewDto(savedOrder);
    }



    private OrderViewDto mapOrderToOrderViewDto(Order order) {
        OrderViewDto dto = new OrderViewDto();
        dto.setOrderId(order.getOrderId());
        //dto.setUserId(order.getUserId);
        dto.setOrderDate(order.getOrderDate());
        dto.setPickupTime(order.getPickupTime());
        dto.setStatus(order.getStatus() != null ? order.getStatus() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setVersion(order.getVersion());

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderOrderId(order.getOrderId());

        if(!CollectionUtils.isEmpty(orderItems)) {
            dto.setItems(orderItems.stream()
                    .map(this::mapOrderItemToOrderItemViewDto)
                    .collect(Collectors.toList()));
        } else {
            dto.setItems(new ArrayList<>());
        }

        return dto;
    }

    private OrderItemViewDto mapOrderItemToOrderItemViewDto(OrderItem orderItem) {
        OrderItemViewDto itemDto = new OrderItemViewDto();
        itemDto.setOderItemId(orderItem.getOrderItemId());
        itemDto.setQuantity(orderItem.getQuantity());
        itemDto.setItemTotalPrice(orderItem.getTotalPrice());
        // TODO: integrate with menuitem, itemvariations

        return itemDto;
    }


}
