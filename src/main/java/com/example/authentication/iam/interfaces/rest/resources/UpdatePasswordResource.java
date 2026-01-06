package com.example.authentication.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents the request body for updating a user's password.
 *
 * @param currentPassword the user's current password, cannot be blank
 * @param newPassword the new password to set, cannot be blank
 */
public record UpdatePasswordResource(
    @NotBlank String currentPassword,
    @NotBlank String newPassword
) {
}
