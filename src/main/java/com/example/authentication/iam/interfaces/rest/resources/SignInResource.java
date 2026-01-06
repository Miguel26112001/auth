package com.example.authentication.iam.interfaces.rest.resources;

/**
 * Represents the request body for signing in a user.
 *
 * @param username the username of the user
 * @param password the password of the user
 */
public record SignInResource(
    String username,
    String password
) {
}
