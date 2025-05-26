package org.psk.lab.menuComponent.api.endpoints;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.api.dto.ItemVariationDto;
import org.psk.lab.menuComponent.api.dto.MenuItemDto;
import org.psk.lab.menuComponent.domain.services.ItemVariationService;
import org.psk.lab.menuComponent.domain.services.MenuItemService;
import org.psk.lab.util.interceptor.LogInvocations;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "Endpoints regarding menu management")
@Validated
@LogInvocations
public class MenuController {
    private final ItemVariationService itemVariationService;
    private final MenuItemService menuItemService;

    @GetMapping("/items")
    public ResponseEntity<Page<MenuItemDto>> getAllMenuItems(
            @ParameterObject @PageableDefault(size = 20, sort = "name,asc") Pageable pageable
    ) {
        Page<MenuItemDto> menuItems = menuItemService.getAll(pageable);

        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<MenuItemDto> getMenuItemById(
            @PathVariable UUID id
    ) {
        MenuItemDto menuItem = menuItemService.getById(id);
        return ResponseEntity.ok(menuItem);
    }

    @PostMapping("/items")
    public ResponseEntity<MenuItemDto> createMenuItem(
            @Valid @RequestBody MenuItemDto menuItemDto
    ) {
        MenuItemDto createdMenuItem = menuItemService.create(menuItemDto);
        return ResponseEntity.ok(createdMenuItem);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<MenuItemDto> updateMenuItem(
            @PathVariable UUID id,
            @Valid @RequestBody MenuItemDto menuItemDto
    ) {
        MenuItemDto updatedMenuItem = menuItemService.update(id, menuItemDto);
        return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable UUID id
    ) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/variations")
    public ResponseEntity<Page<ItemVariationDto>> getAllItemVariations(
            @ParameterObject @PageableDefault(size = 20, sort = "name,asc") Pageable pageable
    ) {
        Page<ItemVariationDto> itemVariations = itemVariationService.getAll(pageable);
        return ResponseEntity.ok(itemVariations);
    }

    @GetMapping("/variations/{id}")
    public ResponseEntity<ItemVariationDto> getItemVariationById(
            @PathVariable UUID id
    ) {
        ItemVariationDto itemVariation = itemVariationService.getById(id);
        return ResponseEntity.ok(itemVariation);
    }

    @PostMapping("/variations")
    public ResponseEntity<ItemVariationDto> createItemVariation(
            @Valid @RequestBody ItemVariationDto itemVariationDto
    ) {
        ItemVariationDto createdItemVariation = itemVariationService.create(itemVariationDto);
        return ResponseEntity.ok(createdItemVariation);
    }

    @PutMapping("/variations/{id}")
    public ResponseEntity<ItemVariationDto> updateItemVariation(
            @PathVariable UUID id,
            @Valid @RequestBody ItemVariationDto itemVariationDto
    ) {
        ItemVariationDto updatedItemVariation = itemVariationService.update(id, itemVariationDto);
        return ResponseEntity.ok(updatedItemVariation);
    }

    @DeleteMapping("/variations/{id}")
    public ResponseEntity<Void> deleteItemVariation(
            @PathVariable UUID id
    ) {
        itemVariationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
