package com.example.authentication.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Represents the request body for registering a new user.
 *
 * @param username the username of the new user, cannot be blank
 * @param email the email of the new user, must be valid and not blank
 * @param password the password of the new user, cannot be blank
 * @param roles the list of roles to assign to the new user
 */
public record SignUpResource(
    @NotBlank String username,
    @NotBlank @Email String email,
    @NotBlank String password,
    List<String> roles
) {
}
