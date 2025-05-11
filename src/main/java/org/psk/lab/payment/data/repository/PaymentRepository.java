package org.psk.lab.payment.data.repository;

import org.psk.lab.payment.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {}

