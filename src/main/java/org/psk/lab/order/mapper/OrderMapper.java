package org.psk.lab.order.mapper;

import lombok.RequiredArgsConstructor;
import org.psk.lab.order.data.dto.OrderItemViewDto;
import org.psk.lab.order.data.dto.OrderSummaryDto;
import org.psk.lab.order.data.dto.OrderViewDto;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.model.OrderItem;
import org.psk.lab.order.data.repository.OrderItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderItemRepository orderItemRepository;

    public OrderViewDto toOrderViewDto(Order order) {
        OrderViewDto dto = new OrderViewDto();
        dto.setOrderId(order.getOrderId());
        //dto.setUserId(order.getUserId);
        dto.setOrderDate(order.getOrderDate());
        dto.setPickupTime(order.getPickupTime());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setVersion(order.getVersion());

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderOrderId(order.getOrderId());

        if(!CollectionUtils.isEmpty(orderItems)) {
            dto.setItems(orderItems.stream()
                    .map(this::toOrderItemViewDto)
                    .collect(Collectors.toList()));
        } else {
            dto.setItems(new ArrayList<>());
        }

        return dto;
    }

    public OrderItemViewDto toOrderItemViewDto(OrderItem orderItem) {
        OrderItemViewDto dto = new OrderItemViewDto();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setItemTotalPrice(orderItem.getTotalPrice());
        // TODO: integrate with menuitem, itemvariations

        return dto;
    }

    public OrderSummaryDto toOrderSummaryDto(Order order) {
        OrderSummaryDto dto = new OrderSummaryDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems == null || orderItems.isEmpty()) {
            orderItems = orderItemRepository.findAllByOrderOrderId(order.getOrderId());
        }
        dto.setItemCount(orderItems != null ? orderItems.size() : 0);

        return dto;
    }
}
