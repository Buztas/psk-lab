package org.psk.lab.order.service;

import jakarta.transaction.Transactional;
import org.psk.lab.order.data.dto.OrderCreateRequestDto;
import org.psk.lab.order.data.dto.OrderItemRequestDto;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.model.OrderItem;
import org.psk.lab.order.data.model.StatusType;
import org.psk.lab.order.data.repository.OrderItemRepository;
import org.psk.lab.order.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository
                        // TODO: inject other repositories
                        ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public Order createOrder(OrderCreateRequestDto requestDto) {
        if (requestDto.getUserID() == null) {
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

            processedOrderItems.add(orderItem);
            //totalOrderAmount = totalOrderAmount.add(lineItemTotalPrice);
        }

        order.setTotalAmount(totalOrderAmount);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : processedOrderItems) {
            item.setOrder(savedOrder);
        }
        orderItemRepository.saveAll(processedOrderItems);

        return savedOrder;

    }
}
