package org.psk.lab.payment.data.dto;

import jakarta.validation.constraints.NotNull;
import org.psk.lab.payment.data.model.PaymentStatus;

public class PaymentStatusUpdateDto {
    @NotNull
    private PaymentStatus status;
    private String transactionId;

    public PaymentStatusUpdateDto() {
    }

    public PaymentStatusUpdateDto(PaymentStatus status, String transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}