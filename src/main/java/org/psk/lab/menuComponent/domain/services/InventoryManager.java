package org.psk.lab.menuComponent.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.domain.entities.interfaces.Stockable;
import org.psk.lab.menuComponent.helper.enums.StockUpdateType;
import org.psk.lab.menuComponent.helper.exceptions.OutOfStockException;
import org.psk.lab.menuComponent.helper.exceptions.ResourceNotFoundException;
import org.psk.lab.menuComponent.repository.ItemVariationRepository;
import org.psk.lab.menuComponent.repository.MenuItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryManager {
    private final MenuItemRepository menuItemRepository;
    private final ItemVariationRepository itemVariationRepository;

    @Transactional
    public void updateMenuItemStock(UUID itemId, int qty, StockUpdateType type) {
        updateStock(menuItemRepository, itemId, qty, type);
    }

    @Transactional
    public void updateItemVariationStock(UUID variationId, int qty, StockUpdateType type) {
        updateStock(itemVariationRepository, variationId, qty, type);
    }

    private <T extends Stockable> void updateStock(
            JpaRepository<T, UUID> repository,
            UUID id,
            int qty,
            StockUpdateType type
    ) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + qty);
        }

        T entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Stockable entity not found with id: " + id)
                );

        switch(type){
            case RESERVE_QUANTITY -> {
                if (entity.getStock() < qty) {
                    throw new OutOfStockException(entity.getClass().getSimpleName(), entity.getStock(), qty, id);
                }
                entity.setStock(entity.getStock() - qty);
            }
            case RELEASE_QUANTITY -> entity.setStock(entity.getStock() + qty);
            default -> throw new IllegalArgumentException("Invalid stock update type: " + type);
        }

        repository.save(entity);
    }
}
