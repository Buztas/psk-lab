package org.psk.lab.menuComponent.domain.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.psk.lab.menuComponent.api.dto.ItemVariationDto;
import org.psk.lab.menuComponent.domain.entities.ItemVariation;
import org.psk.lab.menuComponent.helper.exceptions.ResourceNotFoundException;
import org.psk.lab.menuComponent.helper.mappers.MenuMapper;
import org.psk.lab.menuComponent.repository.ItemVariationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemVariationService {
    private final ItemVariationRepository itemVariationRepository;
    private final MenuMapper menuMapper;

    public Page<ItemVariationDto> getAll(Pageable pageable) {
        return itemVariationRepository.findAll(pageable)
                .map(menuMapper::variationToDto);
    }

    public ItemVariationDto getById(UUID id) {
        ItemVariation itemVariation = itemVariationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ItemVariation not found with id: " + id)
                );
        return menuMapper.variationToDto(itemVariation);
    }

    @Transactional
    public ItemVariationDto create(ItemVariationDto itemVariationDto) {
        if (itemVariationDto.id() != null) {
            throw new IllegalArgumentException("ItemVariation ID should not be specified during creation");
        }
        ItemVariation itemVariation = menuMapper.variationToEntity(itemVariationDto);
        return menuMapper.variationToDto(
                itemVariationRepository.save(itemVariation)
        );
    }

    @Transactional
    public ItemVariationDto update(UUID id, ItemVariationDto itemVariationDto) {
        if (!itemVariationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ItemVariation not found with id: " + id);
        }
        ItemVariation itemVariation = menuMapper.variationToEntity(itemVariationDto);
        itemVariation.setId(id);
        return menuMapper.variationToDto(
                itemVariationRepository.save(itemVariation)
        );
    }

    @Transactional
    public void delete(UUID id) {
        if (!itemVariationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ItemVariation not found with id: " + id);
        }
        itemVariationRepository.deleteById(id);
    }
}
