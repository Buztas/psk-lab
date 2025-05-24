package org.psk.lab.payment.rest;

import com.stripe.model.PaymentIntent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psk.lab.payment.data.dto.PaymentCreateDto;
import org.psk.lab.payment.data.dto.PaymentStatusUpdateDto;
import org.psk.lab.payment.data.dto.PaymentViewDto;
import org.psk.lab.payment.data.dto.StripePaymentRequestDto;
import org.psk.lab.payment.service.PaymentService;
import org.psk.lab.payment.service.StripeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "Endpoints regarding payment management")
public class PaymentController {
    private final PaymentService paymentService;
    private final StripeService stripeService;

    public PaymentController(PaymentService paymentService, StripeService stripeService) {
        this.paymentService = paymentService;
        this.stripeService = stripeService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPayment(@Valid @RequestBody PaymentCreateDto dto) {
        PaymentViewDto payment = paymentService.createPayment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("payment", payment,"clientSecret", payment.getTransactionId())
        );
    }

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<PaymentViewDto> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @Valid @RequestBody PaymentStatusUpdateDto dto
    ) {
        PaymentViewDto updated = paymentService.updatePayment(paymentId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentViewDto> getPayment(@PathVariable UUID paymentId) {
        return paymentService.getPaymentById(paymentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<PaymentViewDto>> getAllPayments(
            @PageableDefault(size = 20, sort = "paymentDate") Pageable pageable
    ) {
        return ResponseEntity.ok(paymentService.getAllPayments(pageable));
    }
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID paymentId, @RequestParam Integer version) {
        paymentService.deletePayment(paymentId, version);
        return ResponseEntity.noContent().build();
    }
}