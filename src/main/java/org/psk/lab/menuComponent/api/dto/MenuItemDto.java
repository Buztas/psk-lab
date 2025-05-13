package org.psk.lab.menuComponent.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.psk.lab.menuComponent.helper.enums.ItemType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
public record MenuItemDto (
    UUID id,
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Size(max = 500) String description,
    @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2) BigDecimal price,
    @NotBlank ItemType type,
    @NotNull @Min(0) int stock,
    @Valid Set<ItemVariationDto> variations
){}
