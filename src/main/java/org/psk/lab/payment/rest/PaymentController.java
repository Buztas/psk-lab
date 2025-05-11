package org.psk.lab.payment.rest;

import lombok.RequiredArgsConstructor;
import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // Create a new payment (when an order is placed)
    @PostMapping
    public ResponseEntity<UUID> createPayment(@RequestBody PaymentDTO dto) {
        UUID paymentId = paymentService.createPayment(dto);
        return ResponseEntity.ok(paymentId);
    }

    // Get a single payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    // Get all payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // Update payment status and transaction ID
    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePayment(
            @PathVariable UUID id,
            @RequestParam("status") PaymentStatus status,
            @RequestParam("transactionId") String transactionId
    ) {
        return ResponseEntity.ok(paymentService.updatePayment(id, status, transactionId));
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }
}