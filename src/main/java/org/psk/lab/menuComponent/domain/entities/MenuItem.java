package org.psk.lab.menuComponent.domain.entities;

import jakarta.persistence.*;
import org.psk.lab.menuComponent.domain.entities.interfaces.Stockable;
import org.psk.lab.menuComponent.helper.enums.ItemType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "menu_item")
public class MenuItem implements Stockable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_item_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Version
    private int version;

    @Column(name = "stock", nullable = false)
    private int stock;

    @ManyToMany
    @JoinTable(
            name = "menu_item_variation",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "item_variation_id")
    )
    private Set<ItemVariation> variations = new HashSet<>();

    // No-arg constructor
    public MenuItem() {}

    // All-args constructor
    public MenuItem(UUID id, String name, String description, BigDecimal price, ItemType type,
                    int version, int stock, Set<ItemVariation> variations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.version = version;
        this.stock = stock;
        this.variations = variations != null ? variations : new HashSet<>();
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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Set<ItemVariation> getVariations() {
        return variations;
    }

    public void setVariations(Set<ItemVariation> variations) {
        this.variations = variations;
    }

    // ✅ Builder class
    public static class Builder {
        private UUID id;
        private String name;
        private String description;
        private BigDecimal price;
        private ItemType type;
        private int version;
        private int stock;
        private Set<ItemVariation> variations = new HashSet<>();

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

        public Builder type(ItemType type) {
            this.type = type;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder variations(Set<ItemVariation> variations) {
            this.variations = variations;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(id, name, description, price, type, version, stock, variations);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
