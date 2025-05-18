package org.psk.lab.payment.mapper;

import org.mapstruct.Mapper;
import org.psk.lab.payment.data.dto.PaymentDTO;
import org.psk.lab.payment.data.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDto(Payment payment);
    Payment toEntity(PaymentDTO dto);
}