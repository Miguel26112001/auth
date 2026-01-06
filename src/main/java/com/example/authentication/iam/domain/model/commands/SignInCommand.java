package com.example.authentication.iam.domain.model.commands;

/**
 * Command used to authenticate a user with a username and password.
 */
public record SignInCommand(
    String username,
    String password) {
}
