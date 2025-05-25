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
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.repository.UserRepository;
import org.psk.lab.user.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/payments")
@Tag(name = "Payment", description = "Endpoints regarding payment management")
public class PaymentController {
    private final PaymentService paymentService;
    private final StripeService stripeService;
    private final UserRepository userRepository;

    public PaymentController(PaymentService paymentService, StripeService stripeService, UserRepository userRepository) {
        this.paymentService = paymentService;
        this.stripeService = stripeService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPayment(@Valid @RequestBody PaymentCreateDto dto) {
        PaymentViewDto payment = paymentService.createPayment(dto);
        String clientSecret = stripeService.getPaymentIntentClientSecret(payment.getTransactionId());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of("payment", payment, "clientSecret", clientSecret)
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

    @GetMapping("/my-payments")
    public ResponseEntity<Page<PaymentViewDto>> getMyPayments(
            Principal principal,
            @PageableDefault(size = 10, sort = "paymentDate,desc") Pageable pageable
    ) {
        String username = principal.getName();
        MyUser user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        UUID userId = user.getUuid();

        Page<PaymentViewDto> myPayments = paymentService.getPaymentsByUserId(userId, pageable);
        return ResponseEntity.ok(myPayments);
    }

}