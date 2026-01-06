package com.example.authentication.iam.domain.model.commands;

/**
 * Command used to update a user's password.
 */
public record UpdatePasswordCommand(
    Long userId,
    String currentPassword,
    String newPassword) {
}
