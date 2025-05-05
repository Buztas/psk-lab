package org.psk.lab.menuComponent.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "item_variation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVariation {
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
    @Builder.Default
    private Set<MenuItem> menuItems = new HashSet<>();

    @Version
    private int version;
}