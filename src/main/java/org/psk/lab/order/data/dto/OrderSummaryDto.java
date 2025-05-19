package org.psk.lab.order.data.dto;

import org.psk.lab.order.data.model.StatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderSummaryDto {

    private UUID orderId;
    private LocalDateTime orderDate;
    private StatusType status;
    private BigDecimal totalAmount;
    private int itemCount;

    public OrderSummaryDto() {}

    public OrderSummaryDto(UUID orderId, LocalDateTime orderDate, StatusType status, BigDecimal totalAmount, int itemCount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.itemCount = itemCount;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return "OrderSummaryDto{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", itemCount=" + itemCount +
                '}';
    }
}
