package org.psk.lab.notification.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.psk.lab.notification.helper.enums.NotificationStatus;
import org.psk.lab.notification.helper.enums.NotificationType;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
public record NotificationDto (
        UUID id,
        @NotBlank @Size(max = 1024) String message,
        @NotNull Timestamp createdAt,
        @NotNull UUID orderId,
        @NotNull NotificationStatus status,
        @NotNull NotificationType type
){
    @Override
    public String toString() {
        return "Notification id: " + id +
                ", message: '" + message + '\'' +
                ", createdAt: " + createdAt +
                ", orderId: " + orderId +
                ", status: " + status +
                ", type: " + type;
    }
}
