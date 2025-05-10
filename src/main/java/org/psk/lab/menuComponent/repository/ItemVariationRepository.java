package org.psk.lab.menuComponent.repository;

import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemVariationRepository extends JpaRepository<ItemVariation, UUID> {
}
