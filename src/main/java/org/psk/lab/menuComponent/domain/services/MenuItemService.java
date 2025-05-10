package org.psk.lab.menuComponent.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.api.dto.MenuItemDto;
import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.psk.lab.menuComponent.helper.mappers.MenuMapper;
import org.psk.lab.menuComponent.repository.MenuItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepo;
    private final MenuMapper menuMapper;

    public Page<MenuItemDto> getAll(Pageable pageable){
        return menuItemRepo.findAll(pageable)
                .map(menuMapper::toDto);
    }

    public MenuItemDto getById(UUID id){
        return menuItemRepo.findById(id)
                .map(menuMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public MenuItemDto create(MenuItemDto menuItemDto){
        MenuItem menuItem = menuMapper.toEntity(menuItemDto);
        if (menuItem.getId() != null) {
            // todo: handle later, should never happen
            menuItem.setId(null);
        }
        return menuMapper.toDto(
                menuItemRepo.save(menuItem)
        );
    }

    @Transactional
    public MenuItemDto update(UUID id, MenuItemDto menuItemDto){
        MenuItem menuItem = menuMapper.toEntity(menuItemDto);
        menuItem.setId(id);
        return menuMapper.toDto(
                menuItemRepo.save(menuItem)
        );
    }

    @Transactional
    public void delete(UUID id){
        menuItemRepo.deleteById(id);
    }
}
