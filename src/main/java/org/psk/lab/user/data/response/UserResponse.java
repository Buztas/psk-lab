package org.psk.lab.user.data.response;

import org.psk.lab.user.data.model.Role;

public record UserResponse(String email,
                           Role roleType,
                           Integer version) {
}
