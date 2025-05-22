package org.psk.lab.user.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.psk.lab.user.data.model.Role;

public record UserUpdateDTO(
        @NotNull @Email String email,
        @JsonProperty Role roleType
) {
}
