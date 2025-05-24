package org.psk.lab.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StripeService {

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, String description) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount.multiply(BigDecimal.valueOf(100)).longValue()) // convert to cents
                    .setCurrency(currency)
                    .setDescription(description)
                    .build();

            return PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new RuntimeException("Stripe payment creation failed", e);
        }
    }
}