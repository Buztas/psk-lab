package org.psk.lab.user.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints regarding user management")
public class UserController {

    @GetMapping
    public String getUser() {
        return "Hello World";
    }
}
