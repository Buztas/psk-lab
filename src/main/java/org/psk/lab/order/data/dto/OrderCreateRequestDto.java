package org.psk.lab.order.data.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderCreateRequestDto {

//    @NotNull(message = "User ID cannot be null.")
//    private UUID userId;

    @NotEmpty(message = "Order must contain at least one item.")
    @Valid
    private List<OrderItemRequestDto> items = new ArrayList<>();

    public OrderCreateRequestDto() {}

    public OrderCreateRequestDto(UUID userId, List<OrderItemRequestDto> items) {
 //       this.userId = userId;
        this.items = items;
    }

//    public UUID getUserId() {
//        return userId;
//    }
//
//    public void setUserId(UUID userId) {
//        this.userId = userId;
//    }

    public List<OrderItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDto> items) {
        this.items = items;
    }
}
