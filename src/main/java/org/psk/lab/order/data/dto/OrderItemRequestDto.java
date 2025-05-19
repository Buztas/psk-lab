package org.psk.lab.order.data.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrderItemRequestDto {

    @NotNull(message = "Menu item ID cannot be null.")
    private UUID menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    private Set<UUID> chosenVariationIds = new HashSet<>();

    public OrderItemRequestDto() {}

    public OrderItemRequestDto(UUID menuItemId, int quantity, Set<UUID> chosenVariationIds) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.chosenVariationIds = chosenVariationIds;
    }

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<UUID> getChosenVariationIds() {
        return chosenVariationIds;
    }

    public void setChosenVariationIds(Set<UUID> chosenVariationIds) {
        this.chosenVariationIds = chosenVariationIds;
    }
}
