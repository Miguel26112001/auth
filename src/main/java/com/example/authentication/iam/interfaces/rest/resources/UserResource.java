package com.example.authentication.iam.interfaces.rest.resources;

import java.util.List;

/**
 * Represents a user resource exposed via the REST API.
 *
 * @param id the unique identifier of the user
 * @param username the username of the user
 * @param email the email address of the user
 * @param roles the list of roles assigned to the user
 * @param isActive indicates whether the user is active
 */
public record UserResource(
    Long id,
    String username,
    String email,
    List<String> roles,
    boolean isActive
) {
}
