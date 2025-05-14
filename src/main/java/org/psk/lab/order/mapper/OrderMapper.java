package org.psk.lab.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.psk.lab.menuComponent.helper.mappers.MenuMapper;
import org.psk.lab.order.data.dto.*;
import org.psk.lab.order.data.model.Order;
import org.psk.lab.order.data.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuMapper.class})
public interface OrderMapper {

    @Mappings({
        @Mapping(source = "myUser.uuid", target = "userId"),
        @Mapping(source = "orderItems", target = "items")
    })
    OrderViewDto toOrderViewDto(Order order);

    @Mappings({
        @Mapping(source = "menuItem.id", target = "menuItemId"),
        @Mapping(source = "menuItem.name", target = "menuItemName"),
        @Mapping(source = "menuItem.price", target = "unitPrice"),
        @Mapping(source = "totalPrice", target = "itemTotalPrice"),
        @Mapping(source = "chosenVariations", target = "chosenVariations")
    })
    OrderItemViewDto toOrderItemViewDto(OrderItem orderItem);

    @Mappings({
        @Mapping(source = "orderItems", target = "itemCount", qualifiedByName = "getItemCountFromList")
    })
    OrderSummaryDto toOrderSummaryDto(Order order);

    @Named("getItemCountFromList")
    default int orderItemsToItemCount(List<OrderItem> orderItems) {
        return orderItems != null ? orderItems.size() : 0;
    }
}
