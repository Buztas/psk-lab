package org.psk.lab.payment.service;

import org.psk.lab.order.data.dto.OrderViewDto;
import org.psk.lab.payment.data.dto.PaymentCreateDto;
import org.psk.lab.payment.data.dto.PaymentStatusUpdateDto;
import org.psk.lab.payment.data.dto.PaymentViewDto;
import org.psk.lab.payment.data.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.psk.lab.payment.data.model.PaymentStatus;

import java.util.Optional;
import java.util.UUID;


public interface PaymentService {
    PaymentViewDto createPayment(PaymentCreateDto dto);
    Optional<PaymentViewDto> getPaymentById(UUID id);
    Page<PaymentViewDto> getAllPayments(Pageable pageable);
    PaymentViewDto updatePayment(UUID id, PaymentStatusUpdateDto dto);
    void deletePayment(UUID id, int version);
    void updateStatusByTransactionId(String transactionId, PaymentStatus newStatus);
    Page<PaymentViewDto> getPaymentsByUserId(UUID userId, Pageable pageable);
}