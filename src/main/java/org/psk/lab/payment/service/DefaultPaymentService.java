package org.psk.lab.payment.service;

import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.data.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultPaymentService implements PaymentService {

    private final PaymentRepository repository;

    public DefaultPaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID createPayment(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setOrder_id(UUID.fromString(dto.orderId()));
        payment.setAmount(dto.amount());
        payment.setPayment_status(PaymentStatus.PENDING);
        payment.setPayment_date(LocalDateTime.now());
        payment.setTransaction_id("pending"); // or empty string

        repository.save(payment);
        return payment.getId();
    }

    @Override
    public Payment getPayment(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    @Override
    public String deletePayment(UUID id) {
        if (!repository.existsById(id)) {
            return "Payment with ID " + id + " not found.";
        }
        repository.deleteById(id);
        return "Payment with ID " + id + " deleted.";
    }

    @Override
    public String updatePayment(UUID id, PaymentStatus status, String transactionId) {
        Payment payment = getPayment(id);
        payment.setPayment_status(status);
        payment.setTransaction_id(transactionId);
        payment.setPayment_date(LocalDateTime.now());
        repository.save(payment);
        return "Payment updated";
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
}