package org.psk.lab.user.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.psk.lab.user.data.dto.UserDTO;
import org.psk.lab.user.data.model.MyUser;
import org.psk.lab.user.data.response.LoginResponse;
import org.psk.lab.user.data.response.UserResponse;
import org.psk.lab.user.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid UserDTO userDTO) {
        return authService.register(userDTO);
    }

    @Operation(summary = "Login user and return JWT Token with User Data")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid UserDTO userDTO) {
        return authService.login(userDTO.email(), userDTO.password());
    }
}
