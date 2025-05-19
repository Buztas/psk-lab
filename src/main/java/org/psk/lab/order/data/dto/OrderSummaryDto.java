package org.psk.lab.order.data.dto;

import lombok.*;
import org.psk.lab.order.data.model.StatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSummaryDto {
    private UUID orderId;
    private LocalDateTime orderDate;
    private StatusType status;
    private BigDecimal totalAmount;
    private int itemCount;
}
