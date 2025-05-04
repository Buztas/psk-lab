package org.psk.lab.order.data.repository;


import org.psk.lab.order.data.model.OrderItemVariation;
import org.psk.lab.order.data.model.OrderItemVariationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemVariationRepository extends JpaRepository<OrderItemVariation, OrderItemVariationId> {

}
