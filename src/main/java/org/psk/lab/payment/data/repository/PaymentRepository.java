package org.psk.lab.payment.data.repository;

import org.psk.lab.payment.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByTransactionId(String transactionId);
    Page<Payment> findAllByOrder_MyUser_Uuid(UUID userId, Pageable pageable);
    @Query("SELECT p FROM Payment p WHERE p.order.orderId = :orderId")
   Optional<Payment> getPaymentByOrderId(UUID orderId);
}
