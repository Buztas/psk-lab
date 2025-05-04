package org.psk.lab.order.data.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OrderItemVariationId implements Serializable {

    @Column(nullable = false)
    private UUID orderItemId;

    @Column(nullable = false)
    private UUID itemVariationId;

    public OrderItemVariationId() {
    }

    public OrderItemVariationId(UUID orderItemId, UUID itemVariationId) {
        this.orderItemId = orderItemId;
        this.itemVariationId = itemVariationId;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public UUID getItemVariationId() {
        return itemVariationId;
    }

    public void setItemVariationId(UUID itemVariationId) {
        this.itemVariationId = itemVariationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemVariationId that = (OrderItemVariationId) o;
        return Objects.equals(orderItemId, that.orderItemId) &&
                Objects.equals(itemVariationId, that.itemVariationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, itemVariationId);
    }

    @Override
    public String toString() {
        return "OrderItemVariationId{" +
                "orderItemId=" + orderItemId +
                ", itemVariationId=" + itemVariationId +
                '}';
    }
}
