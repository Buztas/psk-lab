package org.psk.lab.notification.helper.mapper;

import lombok.Builder;
import org.psk.lab.notification.domain.dtos.NotificationDto;
import org.psk.lab.notification.data.entities.Notification;
import org.springframework.stereotype.Component;

@Builder
@Component
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .createdAt(notification.getCreatedAt())
                .orderId(notification.getOrderId())
                .status(notification.getStatus())
                .type(notification.getType())
                .build();
    }

    public Notification toEntity(NotificationDto dto) {
        return Notification.builder()
                .id(dto.id())
                .message(dto.message())
                .createdAt(dto.createdAt())
                .orderId(dto.orderId())
                .status(dto.status())
                .type(dto.type())
                .build();
    }
}
