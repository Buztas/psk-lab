package org.psk.lab.menuComponent.repository;

import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
