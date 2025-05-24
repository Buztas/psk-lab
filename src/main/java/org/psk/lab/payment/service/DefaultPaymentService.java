package org.psk.lab.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.persistence.OptimisticLockException;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.repository.OrderRepository;
import org.psk.lab.payment.data.dto.PaymentCreateDto;
import org.psk.lab.payment.data.dto.PaymentStatusUpdateDto;
import org.psk.lab.payment.data.dto.PaymentViewDto;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.data.repository.PaymentRepository;
import org.psk.lab.payment.exception.OptimisticPaymentLockException;
import org.psk.lab.payment.exception.PaymentNotFoundException;
import org.psk.lab.payment.mapper.PaymentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultPaymentService implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    public DefaultPaymentService(PaymentRepository paymentRepository,
                                 OrderRepository orderRepository,
                                 PaymentMapper mapper,
                                 StripeService stripeService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.stripeService = stripeService;
    }

    @Override
    @Transactional
    public PaymentViewDto createPayment(PaymentCreateDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        BigDecimal amount = order.getTotalAmount(); // in EUR
        String description = "Payment for order " + order.getOrderId();

        // 1. Create Stripe PaymentIntent
        PaymentIntent intent = stripeService.createPaymentIntent(amount, "eur", description);

        // 2. Create internal Payment record
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setTransactionId(intent.getId()); // Stripe intent ID
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        return mapper.toViewDto(paymentRepository.save(payment));
    }

    @Override
    public Optional<PaymentViewDto> getPaymentById(UUID id) {
        return paymentRepository.findById(id)
                .map(mapper::toViewDto);
    }

    @Override
    public Page<PaymentViewDto> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(mapper::toViewDto);
    }

    @Override
    @Transactional
    public PaymentViewDto updatePayment(UUID id, PaymentStatusUpdateDto dto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        validateStatusTransition(payment.getPaymentStatus(), dto.getStatus());

        payment.setPaymentStatus(dto.getStatus());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaymentDate(LocalDateTime.now());

        try {
            return mapper.toViewDto(paymentRepository.save(payment));
        } catch (OptimisticLockException e) {
            throw new OptimisticPaymentLockException("Payment with ID " + id + " is locked", e);
        }
    }

    @Override
    @Transactional
    public void deletePayment(UUID id, int version) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));

        if (payment.getVersion() != version) {
            throw new OptimisticPaymentLockException("Payment with ID " + id + " has been modified");
        }

        try {
            paymentRepository.delete(payment);
        } catch (OptimisticLockException e) {
            throw new OptimisticPaymentLockException("Payment with ID " + id + " is locked", e);
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

    @Override
    @Transactional
    public void updateStatusByTransactionId(String transactionId, PaymentStatus newStatus) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with transactionId: " + transactionId + " was not found"));

        validateStatusTransition(payment.getPaymentStatus(), newStatus);
        payment.setPaymentStatus(newStatus);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Override
    public Page<PaymentViewDto> getPaymentsByUserId(UUID userId, Pageable pageable) {
        return paymentRepository.findAllByOrder_MyUser_Uuid(userId, pageable)
                .map(mapper::toViewDto);
    }
}