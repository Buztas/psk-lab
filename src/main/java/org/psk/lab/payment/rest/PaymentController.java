package org.psk.lab.payment.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;
import org.psk.lab.payment.data.model.PaymentStatus;
import org.psk.lab.payment.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Endpoints regarding payment management")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<UUID> createPayment(@Valid @RequestBody PaymentDTO dto) {
        try {
            if (dto.orderId() == null || dto.amount() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID and amount are required");
            }

            UUID paymentId = paymentService.createPayment(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPayment(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.getPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable UUID id,
            @RequestParam("status") PaymentStatus status,
            @RequestParam(value = "transactionId", required = false) String transactionId
    ) {
        try {
            String result = paymentService.updatePayment(id, status,
                    transactionId != null ? transactionId : "txn_" + UUID.randomUUID().toString().substring(0, 12));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable UUID id) {
        return ResponseEntity.ok(paymentService.deletePayment(id));
    }
}