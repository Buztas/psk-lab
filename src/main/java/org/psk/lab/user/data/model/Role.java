package org.psk.lab.user.data.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    CUSTOMER,
    EMPLOYEE,
    ADMIN;

    @JsonCreator
    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
