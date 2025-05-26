package org.psk.lab.notification.helper.util;

import org.psk.lab.menuComponent.api.dto.ItemVariationDto;
import org.psk.lab.order.data.dto.OrderItemViewDto;
import org.psk.lab.order.data.dto.OrderViewDto;
import org.psk.lab.util.interceptor.LogInvocations;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@LogInvocations
public class NotificationMessageBuilder {

    public static String buildNotificationMessage(String orderAction, OrderViewDto orderViewDto) {
        BigDecimal totalPrice = orderViewDto.getTotalAmount();
        List<OrderItemViewDto> items = orderViewDto.getItems();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(orderAction);
        messageBuilder.append("Order ID: ").append(orderViewDto.getOrderId()).append("\n");
        messageBuilder.append("Total Price: ").append(totalPrice).append("\n");
        messageBuilder.append("Items:\n");
        items.forEach(item -> {
            var itemVariations = item.getChosenVariations().stream()
                    .map(ItemVariationDto::name)
                    .collect(Collectors.joining(", "));
            messageBuilder.append("\t").append(itemVariations).append("\n");
        }
        );
        return messageBuilder.toString();
    }
}
