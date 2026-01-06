package com.example.authentication.iam.interfaces.rest.resources;

/**
 * Represents the authenticated user details returned after login.
 *
 * @param id       the ID of the authenticated user
 * @param username the username of the authenticated user
 * @param email    the email of the authenticated user
 * @param token    the authentication token
 */
public record AuthenticatedUserResource(
    Long id,
    String username,
    String email,
    String token
) {
}
