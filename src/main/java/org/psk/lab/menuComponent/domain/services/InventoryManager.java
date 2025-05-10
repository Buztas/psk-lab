package org.psk.lab.menuComponent.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.domain.entities.interfaces.Stockable;
import org.psk.lab.menuComponent.helper.enums.StockUpdateType;
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
        T entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        if (type == StockUpdateType.RESERVE_QUANTITY) {
            if (entity.getStock() < qty) {
                // todo handle properly later
                throw new IllegalArgumentException("Not enough stock available");
            }
            entity.setStock(entity.getStock() - qty);
        } else if (type == StockUpdateType.RELEASE_QUANTITY) {
            entity.setStock(entity.getStock() + qty);
        }

        repository.save(entity);
    }
}
