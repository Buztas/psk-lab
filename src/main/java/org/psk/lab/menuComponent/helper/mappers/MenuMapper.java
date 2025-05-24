package org.psk.lab.menuComponent.helper.mappers;

import org.psk.lab.menuComponent.api.dto.ItemVariationDto;
import org.psk.lab.menuComponent.api.dto.MenuItemDto;
import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.psk.lab.menuComponent.domain.entities.MenuItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public MenuItemDto toDto(MenuItem entity) {
        if (entity == null) return null;
        return MenuItemDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .type(entity.getType())
                .stock(entity.getStock())
                .version(entity.getVersion())
                .variations(entity.getVariations().stream()
                        .map(this::variationToDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public MenuItem toEntity(MenuItemDto dto) {
        if (dto == null) return null;
        return MenuItem.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .type(dto.type())
                .stock(dto.stock())
                .version(dto.version())
                .variations(dto.variations().stream()
                        .map(this::variationToEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    public ItemVariationDto variationToDto(ItemVariation entity) {
        if (entity == null) return null;
        return ItemVariationDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .version(entity.getVersion())
                .build();
    }

    public ItemVariation variationToEntity(ItemVariationDto dto) {
        if (dto == null) return null;
        return ItemVariation.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .stock(dto.stock())
                .version(dto.version())
                .build();
    }
}
