package org.psk.lab.menuComponent.helper.mappers;

import org.mapstruct.Mapper;
import org.psk.lab.menuComponent.api.dto.ItemVariationDto;
import org.psk.lab.menuComponent.api.dto.MenuItemDto;
import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.psk.lab.menuComponent.domain.entities.MenuItem;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuItemDto toDto(MenuItem entity);
    MenuItem toEntity(MenuItemDto dto);
    ItemVariationDto variationToDto(ItemVariation entity);
    ItemVariation variationToEntity(ItemVariationDto dto);
}
