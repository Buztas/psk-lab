package org.psk.lab.menuComponent.domain.entities;

import jakarta.persistence.*;
import org.psk.lab.menuComponent.domain.entities.interfaces.Stockable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "item_variation")
public class ItemVariation implements Stockable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_variation_id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @ManyToMany(mappedBy = "variations")
    private Set<MenuItem> menuItems = new HashSet<>();

    @Version
    private int version;

    // No-arg constructor
    public ItemVariation() {}

    // All-args constructor
    public ItemVariation(UUID id, String name, String description, BigDecimal price,
                         int stock, Set<MenuItem> menuItems, int version) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.menuItems = menuItems != null ? menuItems : new HashSet<>();
        this.version = version;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    // ✅ Builder
    public static class Builder {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private int stock;
        private Set<MenuItem> menuItems = new HashSet<>();
        private int version;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder menuItems(Set<MenuItem> menuItems) {
            this.menuItems = menuItems;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public ItemVariation build() {
            return new ItemVariation(id, name, description, price, stock, menuItems, version);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
