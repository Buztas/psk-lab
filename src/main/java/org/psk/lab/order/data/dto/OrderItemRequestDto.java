package org.psk.lab.order.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    @NotNull(message = "Menu item ID cannot be null.")
    private UUID menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    private Set<UUID> chosenVariationIds = new HashSet<>();

}
