package org.psk.lab.menuComponent.helper.exceptions;

import java.util.UUID;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String itemName, int stock, int qty, UUID id) {
        super(String.format("%s out of stock (id: %s): %d in stock, %d requested", itemName, id, stock, qty));
    }
}
