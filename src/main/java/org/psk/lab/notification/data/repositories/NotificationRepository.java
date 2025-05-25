package org.psk.lab.notification.data.repositories;

import org.psk.lab.notification.data.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Optional<Notification> findByOrderId(UUID orderId);
}
