package org.psk.lab.payment.service;

import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    UUID createPayment(PaymentDTO dto);
    Payment getPayment(UUID id);
    List<Payment> getAllPayments();
    String deletePayment(UUID id);
    String updatePayment(UUID id, PaymentStatus status, String transactionId);
}
