package org.psk.lab.order.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_item_id", nullable = false, updatable = false)
    private UUID orderItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "menu_item_id", nullable = false)
//    private MenuItem menuItem;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

//    @ManyToMany
//    @JoinTable(
//            name = "order_item_variation",
//            joinColumns = @JoinColumn(name = "order_item_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_variation_id")
//    )
//    private Set<ItemVariation> chosenVariations = new HashSet<>();

    public OrderItem() {
    }

    public OrderItem(Order order, int quantity, BigDecimal totalPrice) {
        this.order = order;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

//    public MenuItem getMenuItem(){
//        return menuItem;
//    }
//
//    public void setMenuItem(MenuItem menuItem) {
//        this.menuItem = menuItem;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        if (this.orderItemId == null || orderItem.orderItemId == null) {
            return false;
        }
        return orderItemId.equals(orderItem.orderItemId);
    }

    @Override
    public int hashCode() {
        return orderItemId != null ? orderItemId.hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", orderId=" + (order != null ? order.getOrderId() : "null") +
                // ", menuItemId=" + (menuItem != null ? menuItem.getMenuItemId() : "null") +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
