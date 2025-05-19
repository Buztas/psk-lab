package org.psk.lab.order.data.model;

import jakarta.persistence.*;
import org.psk.lab.user.data.model.MyUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MyUser myUser;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    // No-arg constructor
    public Order() {
    }

    // All-arg constructor
    public Order(UUID orderId, MyUser myUser, LocalDateTime orderDate, LocalDateTime pickupTime,
                 StatusType status, BigDecimal totalAmount, Integer version, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.myUser = myUser;
        this.orderDate = orderDate;
        this.pickupTime = pickupTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.version = version;
        this.orderItems = orderItems;
    }

    // Getters and Setters
    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static class Builder {
        private UUID orderId;
        private MyUser myUser;
        private LocalDateTime orderDate;
        private LocalDateTime pickupTime;
        private StatusType status;
        private BigDecimal totalAmount;
        private Integer version;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder myUser(MyUser myUser) {
            this.myUser = myUser;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder pickupTime(LocalDateTime pickupTime) {
            this.pickupTime = pickupTime;
            return this;
        }

        public Builder status(StatusType status) {
            this.status = status;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(orderId, myUser, orderDate, pickupTime, status, totalAmount, version, orderItems);
        }

        public static Builder builder() {
            return new Builder();
        }
    }
}
