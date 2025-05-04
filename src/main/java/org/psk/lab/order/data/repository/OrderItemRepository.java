package org.psk.lab.order.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.psk.lab.order.data.model.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findAllByOrderId(UUID orderId);
}
