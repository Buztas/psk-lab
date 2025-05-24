package org.psk.lab.payment.data.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class StripePaymentRequestDto {
    @NotNull
    private BigDecimal amount;

    private String currency = "eur";
    private String description = "Stripe Payment for Order";

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}