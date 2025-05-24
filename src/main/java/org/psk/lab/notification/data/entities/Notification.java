package org.psk.lab.notification.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.psk.lab.notification.helper.enums.NotificationStatus;
import org.psk.lab.notification.helper.enums.NotificationType;

import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "notification_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT", length = 1024)
    private String message;

    @Column(name = "sent_at", nullable = false)
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NotificationType type;
}
