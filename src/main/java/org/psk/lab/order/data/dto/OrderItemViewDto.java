package org.psk.lab.order.data.dto;

import org.psk.lab.menuComponent.api.dto.ItemVariationDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrderItemViewDto {

    private UUID orderItemId;
    private UUID menuItemId;
    private String menuItemName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal itemTotalPrice;
    private Set<ItemVariationDto> chosenVariations = new HashSet<>();

    public OrderItemViewDto() {}

    public OrderItemViewDto(UUID orderItemId, UUID menuItemId, String menuItemName, int quantity,
                            BigDecimal unitPrice, BigDecimal itemTotalPrice, Set<ItemVariationDto> chosenVariations) {
        this.orderItemId = orderItemId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.itemTotalPrice = itemTotalPrice;
        this.chosenVariations = chosenVariations;
    }

    public UUID getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(UUID orderItemId) {
        this.orderItemId = orderItemId;
    }

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(BigDecimal itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public Set<ItemVariationDto> getChosenVariations() {
        return chosenVariations;
    }

    public void setChosenVariations(Set<ItemVariationDto> chosenVariations) {
        this.chosenVariations = chosenVariations;
    }

    @Override
    public String toString() {
        return "OrderItemViewDto{" +
                "orderItemId=" + orderItemId +
                ", menuItemId=" + menuItemId +
                ", menuItemName='" + menuItemName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", itemTotalPrice=" + itemTotalPrice +
                ", chosenVariations=" + chosenVariations +
                '}';
    }
}
