package org.psk.lab.order.data.dto;


import lombok.*;
import org.psk.lab.order.data.model.StatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderViewDto {

    private UUID orderId;
    private UUID userId;
    private LocalDateTime orderDate;
    private LocalDateTime pickupTime;
    private StatusType status;
    private BigDecimal totalAmount;
    private List<OrderItemViewDto> items = new ArrayList<>();
    private int version;
}
