package com.example.authentication.iam.domain.model.commands;

/**
 * Command used to verify a user account using a verification token.
 */
public record VerifyUserCommand(String token) {
}
