package org.psk.lab.order.data.repository;

import org.psk.lab.order.data.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByMyUser_Uuid(UUID uuid, Pageable pageable);
}
