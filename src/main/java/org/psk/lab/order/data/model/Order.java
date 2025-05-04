package org.psk.lab.order.data.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID orderId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private User user;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    private LocalDateTime pickupTime;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(nullable = false)
    private float totalAmount;

    //@Version cia reikia sito ar ne?
    private int version;

    public Order() {
    }

//    public Order (User user, LocalDateTime orderDate, LocalDateTime pickupTime, StatusType status, float totalAmount) {
//        this.user = user;
//        this.orderDate = orderDate;
//        this.pickupTime = pickupTime;
//        this.status = status;
//        this.totalAmount = totalAmount;
//    }

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

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", version=" + version +
                '}';
    }
}
