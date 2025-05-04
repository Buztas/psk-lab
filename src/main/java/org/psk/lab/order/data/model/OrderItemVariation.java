package org.psk.lab.order.data.model;

import jakarta.persistence.*;
//import org.psk.lab.menu.data.model.ItemVariation;

import java.util.Objects;

@Entity
public class OrderItemVariation {

    @EmbeddedId
    private OrderItemVariationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderItemId")
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private OrderItem orderItem;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("itemVariationId")
//    @JoinColumn(nullable = false, updatable = false, insertable = false)
//    private ItemVariation itemVariation;

    public OrderItemVariation() {
    }

    public OrderItemVariationId getId() {
        return id;
    }

    public void setId(OrderItemVariationId id) {
        this.id = id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

//    public ItemVariation getItemVariation() {
//        return itemVariation;
//    }
//
//    public void setItemVariation(ItemVariation itemVariation) {
//        this.itemVariation = itemVariation;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemVariation that = (OrderItemVariation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItemVariation{" +
                "id=" + id +
                ", orderItemId=" + (orderItem != null ? orderItem.getOrderItemId() : "null") +
                // ", itemVariationId=" + (itemVariation != null ? itemVariation.getItemVariationId() : "null") +
                '}';
    }
}
