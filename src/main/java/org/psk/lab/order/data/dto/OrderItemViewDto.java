package org.psk.lab.order.data.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemViewDto {

    private UUID oderItemId;
    private UUID menuItemId;
    private String menuItemName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal itemTotalPrice;
    //private Set<ItemVariationDto> chosenVariations = new HashSet<>();
}
