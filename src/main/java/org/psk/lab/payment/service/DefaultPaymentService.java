package org.psk.lab.payment.service;

import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.data.repository.PaymentRepository;
import org.psk.lab.payment.exception.OptimisticPaymentLockException;
import org.psk.lab.payment.exception.PaymentNotFoundException;
import org.psk.lab.payment.mapper.PaymentMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultPaymentService implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    public DefaultPaymentService(PaymentRepository repository, PaymentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public UUID createPayment(PaymentDTO dto) {
        Payment payment = mapper.toEntity(dto);

        // Set default values if not provided
        if (payment.getPaymentStatus() == null) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
        }
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        if (payment.getTransactionId() == null) {
            payment.setTransactionId("txn_" + UUID.randomUUID().toString().substring(0, 12));
        }

        return repository.save(payment).getId();
    }

    @Override
    public Payment getPayment(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Override
    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public String deletePayment(UUID id) {
        try {
            Payment payment = repository.findById(id)
                    .orElseThrow(() -> new PaymentNotFoundException(id));
            repository.delete(payment);
            return "Payment with ID " + id + " was deleted successfully";
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticPaymentLockException("Payment was modified concurrently. Cannot delete.", e);
        }
    }

    @Override
    @Transactional
    public String updatePayment(UUID id, PaymentStatus status, String transactionId) {
        try {
            Payment payment = repository.findById(id)
                    .orElseThrow(() -> new PaymentNotFoundException(id));

            validateStatusTransition(payment.getPaymentStatus(), status);

            payment.setPaymentStatus(status);
            payment.setTransactionId(transactionId);
            payment.setPaymentDate(LocalDateTime.now());

            repository.save(payment);
            return "Payment with ID " + id + " was updated to status: " + status;
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new OptimisticPaymentLockException("Payment was updated concurrently. Please retry.", e);
        }
    }

    private void validateStatusTransition(PaymentStatus currentStatus, PaymentStatus newStatus) {
        if (currentStatus == PaymentStatus.COMPLETED || currentStatus == PaymentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status from " + currentStatus + " to " + newStatus);
        }

        if (newStatus == PaymentStatus.PROCESSING && currentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("Can only process payments from PENDING status");
        }

        if (newStatus == PaymentStatus.COMPLETED && currentStatus != PaymentStatus.PROCESSING) {
            throw new IllegalStateException("Can only complete payments from PROCESSING status");
        }
    }
}