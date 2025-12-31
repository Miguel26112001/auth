package com.example.authentication.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record SignUpResource(
    @NotBlank String username,
    @NotBlank @Email String email,
    @NotBlank String password,
    List<String> roles) {
}
