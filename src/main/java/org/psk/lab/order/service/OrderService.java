package org.psk.lab.order.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.psk.lab.menuComponent.helper.exceptions.ResourceNotFoundException;
import org.psk.lab.menuComponent.repository.ItemVariationRepository;
import org.psk.lab.menuComponent.repository.MenuItemRepository;
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
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final ItemVariationRepository itemVariationRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderViewDto createOrder(OrderCreateRequestDto requestDto) {
        Order order = new Order();
        MyUser myUser = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        order.setMyUser(myUser);
        order.setOrderDate(LocalDateTime.now());
        order.setPickupTime(LocalDateTime.now().plusMinutes(10));
        order.setStatus(StatusType.PENDING);
        List<OrderItem> processedOrderItems = new ArrayList<>();
        BigDecimal totalOrderAmount = BigDecimal.ZERO;

        for (OrderItemRequestDto itemDto : requestDto.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found" + itemDto.getMenuItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());

            Set<ItemVariation> chosenVariations = new HashSet<>();
            for (UUID variationId : itemDto.getChosenVariationIds()) {
                ItemVariation variation = itemVariationRepository.findById(variationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Variation not found: " + variationId));
                chosenVariations.add(variation);
            }
            orderItem.setChosenVariations(chosenVariations);

            BigDecimal basePrice = menuItem.getPrice();
            BigDecimal variationsTotal = chosenVariations.stream()
                    .map(ItemVariation::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal lineTotal = (basePrice.add(variationsTotal)).multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            orderItem.setTotalPrice(lineTotal);

            totalOrderAmount = totalOrderAmount.add(lineTotal);
            processedOrderItems.add(orderItem);
        }

        order.setOrderItems(processedOrderItems);
        order.setTotalAmount(totalOrderAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderViewDto(savedOrder);
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
        StatusType newStatus;
        try {
            newStatus = StatusType.valueOf(requestDto.getNewStatus().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusValueException("Unknown status: " + requestDto.getNewStatus(), ex);
        }

        try {
            Order base = orderRepository.findById(orderId)
                    .orElseThrow(() -> new OrderNotFoundException("Order " + orderId + " not found"));

            Order detached = Order.Builder.builder()
                    .orderId(orderId)
                    .version(requestDto.getVersion())
                    .status(newStatus)
                    .myUser(base.getMyUser())
                    .orderDate(base.getOrderDate())
                    .pickupTime(base.getPickupTime())
                    .totalAmount(base.getTotalAmount())
                    .orderItems(base.getOrderItems())
                    .build();

            Order merged = orderRepository.save(detached);

            return orderMapper.toOrderViewDto(merged);

        } catch (ObjectOptimisticLockingFailureException | OptimisticLockException ex) {
            throw new OptimisticLockingConflictException(
                    "Order update failed – it was changed by someone else.", ex);
        }
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        orderRepository.deleteById(orderId);
    }

    public MyUser getUserByOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(Order::getMyUser)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
