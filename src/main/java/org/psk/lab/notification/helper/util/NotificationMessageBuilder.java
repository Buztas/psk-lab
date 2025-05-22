package org.psk.lab.notification.helper.util;

import org.psk.lab.order.data.dto.OrderItemViewDto;
import org.psk.lab.order.data.dto.OrderViewDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class NotificationMessageBuilder {

    public static String buildNotificationMessage(OrderViewDto orderViewDto) {
        BigDecimal totalPrice = orderViewDto.getTotalAmount();
        List<OrderItemViewDto> items = orderViewDto.getItems();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Your order has been fulfilled!\n");
        messageBuilder.append("Order ID: ").append(orderViewDto.getOrderId()).append("\n");
        messageBuilder.append("Total Price: ").append(totalPrice).append("\n");
        messageBuilder.append("Items:\n");
        items.forEach(item ->
                messageBuilder.append(item.toString()).append("\n")
        );
        messageBuilder.append("Thank you for your order!");
        return messageBuilder.toString();
    }
}
