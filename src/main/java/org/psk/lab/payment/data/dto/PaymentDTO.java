package org.psk.lab.payment.data.dto;

import java.math.BigDecimal;

public record PaymentDTO(String orderId, BigDecimal amount, String method) {}