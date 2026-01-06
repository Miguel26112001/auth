package com.example.authentication.iam.domain.model.commands;

/**
 * Command used to update a user's active status.
 */
public record UpdateUserStatusCommand(
    Long userId,
    boolean isActive) {
}
