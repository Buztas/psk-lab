package org.psk.lab.payment.mapper;

import org.psk.lab.order.data.model.Order;
import org.psk.lab.payment.data.dto.PaymentCreateDto;
import org.psk.lab.payment.data.dto.PaymentStatusUpdateDto;
import org.psk.lab.payment.data.dto.PaymentViewDto;
import org.psk.lab.payment.data.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentCreateDto dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        // Other fields are set by service layer
        return payment;
    }

    public PaymentViewDto toViewDto(Payment payment) {
        PaymentViewDto dto = new PaymentViewDto();
        if (payment != null) {
            dto.setId(payment.getId());
            if (payment.getOrder() != null) {
                dto.setOrderId(payment.getOrder().getOrderId());
            }
            dto.setAmount(payment.getAmount());
            dto.setPaymentStatus(payment.getPaymentStatus());
            dto.setTransactionId(payment.getTransactionId());
            dto.setPaymentDate(payment.getPaymentDate());
            dto.setVersion(payment.getVersion());
        }
        return dto;
    }

    public Payment updateStatusDtoToPayment(PaymentStatusUpdateDto dto) {
        Payment payment = new Payment();
        if (dto != null) {
            payment.setPaymentStatus(dto.getStatus());
            payment.setTransactionId(dto.getTransactionId());
        }
        return payment;
    }
}