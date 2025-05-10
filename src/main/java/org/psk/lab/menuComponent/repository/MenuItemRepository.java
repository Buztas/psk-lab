package org.psk.lab.menuComponent.repository;

import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
}
