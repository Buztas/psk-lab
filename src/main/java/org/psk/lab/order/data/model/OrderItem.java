package org.psk.lab.order.data.model;

import jakarta.persistence.*;
import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.psk.lab.menuComponent.domain.entities.MenuItem;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(
            name = "order_item_variation",
            joinColumns = @JoinColumn(name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "item_variation_id")
    )
    private Set<ItemVariation> chosenVariations = new HashSet<>();

    // No-arg constructor
    public OrderItem() {}

    // All-args constructor
    public OrderItem(UUID orderItemId, Order order, MenuItem menuItem, int quantity,
                     BigDecimal totalPrice, Set<ItemVariation> chosenVariations) {
        this.orderItemId = orderItemId;
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.chosenVariations = chosenVariations;
    }

    // Getters and Setters

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

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

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

    public Set<ItemVariation> getChosenVariations() {
        return chosenVariations;
    }

    public void setChosenVariations(Set<ItemVariation> chosenVariations) {
        this.chosenVariations = chosenVariations;
    }
}
