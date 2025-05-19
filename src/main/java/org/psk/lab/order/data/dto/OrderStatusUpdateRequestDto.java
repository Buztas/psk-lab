package org.psk.lab.order.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderStatusUpdateRequestDto {

    @NotBlank(message = "New status cannot be blank.")
    private String newStatus;

    private Integer version;

    public OrderStatusUpdateRequestDto() {}

    public OrderStatusUpdateRequestDto(String newStatus, Integer version) {
        this.newStatus = newStatus;
        this.version = version;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OrderStatusUpdateRequestDto{" +
                "newStatus='" + newStatus + '\'' +
                ", version=" + version +
                '}';
    }
}
