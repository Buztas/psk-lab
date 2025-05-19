package org.psk.lab.order.data.dto;

import org.psk.lab.order.data.model.StatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderViewDto {

    private UUID orderId;
    private UUID userId;
    private LocalDateTime orderDate;
    private LocalDateTime pickupTime;
    private StatusType status;
    private BigDecimal totalAmount;
    private List<OrderItemViewDto> items = new ArrayList<>();
    private Integer version;

    public OrderViewDto() {}

    public OrderViewDto(UUID orderId, UUID userId, LocalDateTime orderDate, LocalDateTime pickupTime,
                        StatusType status, BigDecimal totalAmount, List<OrderItemViewDto> items, Integer version) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.pickupTime = pickupTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
        this.version = version;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        this.pickupTime = pickupTime;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemViewDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemViewDto> items) {
        this.items = items;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OrderViewDto{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", orderDate=" + orderDate +
                ", pickupTime=" + pickupTime +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", items=" + items +
                ", version=" + version +
                '}';
    }
}
