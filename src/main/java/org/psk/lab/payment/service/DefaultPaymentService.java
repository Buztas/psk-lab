package org.psk.lab.payment.service;

import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.data.repository.PaymentRepository;
import org.psk.lab.payment.exception.PaymentNotFoundException;
import org.psk.lab.payment.mapper.PaymentMapper;
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
        if (!repository.existsById(id)) {
            throw new PaymentNotFoundException(id);
        }
        repository.deleteById(id);
        return "Payment with ID " + id + " was deleted successfully";
    }

    @Override
    @Transactional
    public String updatePayment(UUID id, PaymentStatus status, String transactionId) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        validateStatusTransition(payment.getPaymentStatus(), status);

        payment.setPaymentStatus(status);
        payment.setTransactionId(transactionId);
        payment.setPaymentDate(LocalDateTime.now());

        repository.save(payment);
        return "Payment with ID " + id + " was updated to status: " + status;
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