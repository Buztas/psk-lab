package org.psk.lab.order.data.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderStatusUpdateRequestDto {

    @NotBlank(message = "New status cannot be blank.")
    private String newStatus;

    @NotNull(message = "Version is required for optimistic locking.")
    private Integer version;
}
