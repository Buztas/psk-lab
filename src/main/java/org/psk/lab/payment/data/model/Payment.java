package org.psk.lab.payment.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID order_id;
    /*
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order_id;
    */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus payment_status;
    @Column(nullable = false)
    private LocalDateTime payment_date;
    @Column(nullable = false)
    private String transaction_id;

    public Payment() {
    }

    public Payment(UUID id, UUID order_id, BigDecimal amount, PaymentStatus payment_status,
                   LocalDateTime payment_date, String transaction_id) {
        this.id = id;
        this.order_id = order_id;
        this.amount = amount;
        this.payment_status = payment_status;
        this.payment_date = payment_date;
        this.transaction_id = transaction_id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrder_id() {
        return order_id;
    }

    public void setOrder_id(UUID order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(PaymentStatus payment_status) {
        this.payment_status = payment_status;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(order_id, payment.order_id) &&
                Objects.equals(amount, payment.amount) &&
                payment_status == payment.payment_status &&
                Objects.equals(payment_date, payment.payment_date) &&
                Objects.equals(transaction_id, payment.transaction_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order_id, amount, payment_status, payment_date, transaction_id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", amount=" + amount +
                ", payment_status=" + payment_status +
                ", payment_date=" + payment_date +
                ", transaction_id='" + transaction_id + '\'' +
                '}';
    }
}