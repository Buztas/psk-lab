package org.psk.lab.order.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {

    @NotNull(message = "User ID cannot be null.")
    private UUID userId;

    @NotEmpty(message = "Order must contain at least one item.")
    @Valid
    private List<OrderItemRequestDto> items = new ArrayList<>();

}
