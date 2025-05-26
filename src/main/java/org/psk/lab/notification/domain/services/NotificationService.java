package org.psk.lab.notification.domain.services;

import jakarta.transaction.Transactional;
import org.psk.lab.notification.domain.dtos.NotificationDto;
import org.psk.lab.notification.data.entities.Notification;
import org.psk.lab.notification.domain.strategies.NotificationStrategy;
import org.psk.lab.notification.helper.enums.NotificationStatus;
import org.psk.lab.notification.helper.enums.NotificationType;
import org.psk.lab.notification.data.repositories.NotificationRepository;
import org.psk.lab.notification.helper.exceptions.FaultyNotificationException;
import org.psk.lab.notification.helper.exceptions.NotificationFailedException;
import org.psk.lab.notification.helper.mapper.NotificationMapper;
import org.psk.lab.notification.helper.util.NotificationMessageBuilder;
import org.psk.lab.order.data.dto.OrderViewDto;
import org.psk.lab.order.service.OrderService;
import org.psk.lab.util.interceptor.LogInvocations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing notifications.
 * This class is responsible for creating, sending, and updating notifications.
 * To send a notification:
 * 1. call dto = createNotification(orderId, type) to create & save a new notification,
 * 2. then call send(dto) to send it.
 */
@Service
@Transactional
@LogInvocations
public class NotificationService {
    private final NotificationMapper notificationMapper;
    private final OrderService orderService;
    private final NotificationRepository notificationRepository;
    private final Map<NotificationType, NotificationStrategy> strategies;

    public NotificationService(
            NotificationRepository notificationRepository,
            List<NotificationStrategy> strategies,
            OrderService orderService,
            NotificationMapper notificationMapper
    ) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
        this.orderService = orderService;
        this.strategies = strategies.stream()
                .collect(Collectors.toUnmodifiableMap(NotificationStrategy::getType, strategy -> strategy));
    }

    public void testSend(){
        NotificationDto notificationDto = new NotificationDto(
                UUID.randomUUID(),
                "Test order id",
                new Timestamp(System.currentTimeMillis()),
                UUID.randomUUID(),
                NotificationStatus.PENDING,
                NotificationType.EMAIL
        );
        NotificationStrategy strategy = strategies.get(NotificationType.EMAIL);
        strategy.send(notificationDto);
    }

    @Transactional
    public NotificationDto send(NotificationDto notificationDto) throws NotificationFailedException {
        // find strategy
        NotificationStrategy strategy = strategies.get(notificationDto.type());

        // update notification status
        if(strategy.send(notificationDto)) {
            // sent successfully
            updateNotificationStatus(notificationDto.id(), NotificationStatus.SENT);
        } else {
            // sent failed
            updateNotificationStatus(notificationDto.id(), NotificationStatus.FAILED);
            throw new NotificationFailedException(notificationDto.type(), notificationDto.id(), notificationDto.message());
        }

        return notificationDto;
    }

    /*
        * Create a new notification for the given order and type.
        * Builds the notification message using the order details.
     */
    @Transactional
    public NotificationDto createNotification(UUID orderId, NotificationType type, String message) throws FaultyNotificationException {
        Notification notification = new Notification();
        Optional<OrderViewDto> order = orderService.getOrderById(orderId);
        if (order.isEmpty()) {
            throw new FaultyNotificationException("InvalidOrder, id: " + orderId);
        }
        // build notification message
        String msg = NotificationMessageBuilder.buildNotificationMessage(message, order.get());

        // build notification
        notification.setMessage(msg);
        notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        notification.setOrderId(orderId);
        notification.setStatus(NotificationStatus.PENDING);
        notification.setType(type);
        notification.setOrderId(orderId);

        // save notification to db
        notification = notificationRepository.save(notification);

        return notificationMapper.toDto(notification);
    }

    @Transactional
    public void updateNotificationStatus(UUID notificationId, NotificationStatus status) throws FaultyNotificationException {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isEmpty()) {
            throw new FaultyNotificationException("Notification not found, id: " + notificationId);
        }

        // update notification status
        notification.get().setStatus(status);

        // save notification to db
        notification = Optional.of(notificationRepository.save(notification.get()));

        notificationMapper.toDto(notification.get());
    }

    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<NotificationDto> getNotificationById(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .map(notificationMapper::toDto);
    }

    public Optional<NotificationDto> getNotificationByOrderId(UUID orderId) {
        return notificationRepository.findByOrderId((orderId))
                .map(notificationMapper::toDto);
    }
}
