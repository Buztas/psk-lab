package org.psk.lab.order.data.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID orderItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Order order;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(nullable = false)
//    private MenuItem menuItem;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float totalPrice;

//    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<OrderItemVariation> variations = new ArrayList<>();

    public OrderItem() {
    }

    public OrderItem(Order order, int quantity, float totalPrice) {
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public List<OrderItemVariation> getVariations() {
//        return variations;
//     }
//    public void setVariations(List<OrderItemVariation> variations) {
//       this.variations = variations;
//    }

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
