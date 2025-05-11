package org.psk.lab.user.data.response;

public record LoginResponse(String token, UserResponse user) {
}
