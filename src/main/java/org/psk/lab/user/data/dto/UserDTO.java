package org.psk.lab.user.data.dto;

import jakarta.validation.constraints.NotNull;
import org.psk.lab.user.data.model.Role;

public record UserDTO(@NotNull String email,
                      @NotNull String password,
                      Role roleType,
                      Integer version) {
    public UserDTO {
        if (roleType == null) roleType = Role.CUSTOMER;
        if (version == null) version = 1;
    }
}
