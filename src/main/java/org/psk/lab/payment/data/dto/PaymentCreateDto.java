package org.psk.lab.payment.data.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentCreateDto {
    @NotNull
    private UUID orderId;
    @NotNull
    private BigDecimal amount;

    public PaymentCreateDto() {
    }

    public PaymentCreateDto(UUID orderId, BigDecimal amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}