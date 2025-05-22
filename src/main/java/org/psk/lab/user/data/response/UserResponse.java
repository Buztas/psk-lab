package org.psk.lab.user.data.response;

import org.psk.lab.user.data.model.Role;

import java.util.UUID;

public record UserResponse(UUID id,
                           String email,
                           Role roleType,
                           Integer version) {
}
