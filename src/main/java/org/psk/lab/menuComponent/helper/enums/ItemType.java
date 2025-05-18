package org.psk.lab.menuComponent.helper.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    FOOD("Food"),
    DRINK("Drink");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }
}
