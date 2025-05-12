package org.psk.lab.menuComponent.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.api.dto.MenuItemDto;
import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.psk.lab.menuComponent.helper.exceptions.ResourceNotFoundException;
import org.psk.lab.menuComponent.helper.mappers.MenuMapper;
import org.psk.lab.menuComponent.repository.MenuItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuMapper menuMapper;

    public Page<MenuItemDto> getAll(Pageable pageable){
        return menuItemRepository.findAll(pageable)
                .map(menuMapper::toDto);
    }

    public MenuItemDto getById(UUID id){
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("MenuItem not found with id: " + id)
                );
        return menuMapper.toDto(menuItem);
    }

    @Transactional
    public MenuItemDto create(MenuItemDto menuItemDto) {
        if (menuItemDto.id() != null) {
            throw new IllegalArgumentException("MenuItem ID should not be specified during creation");
        }
        MenuItem menuItem = menuMapper.toEntity(menuItemDto);
        return menuMapper.toDto(
                menuItemRepository.save(menuItem)
        );
    }

    @Transactional
    public MenuItemDto update(UUID id, MenuItemDto menuItemDto){
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("MenuItem not found with id: " + id);
        }
        MenuItem menuItem = menuMapper.toEntity(menuItemDto);
        menuItem.setId(id);
        return menuMapper.toDto(
                menuItemRepository.save(menuItem)
        );
    }

    @Transactional
    public void delete(UUID id){
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("MenuItem not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
