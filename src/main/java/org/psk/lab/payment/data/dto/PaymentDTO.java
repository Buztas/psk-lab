package org.psk.lab.payment.data.dto;

import org.psk.lab.payment.data.model.PaymentStatus;

import java.math.BigDecimal;

public record PaymentDTO(
        String orderId,
        BigDecimal amount,
        PaymentStatus status,
        String transactionId
) {}