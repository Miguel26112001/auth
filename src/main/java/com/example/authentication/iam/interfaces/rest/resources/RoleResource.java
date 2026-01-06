package com.example.authentication.iam.interfaces.rest.resources;

/**
 * Represents a role in the system.
 *
 * @param id   the ID of the role
 * @param name the name of the role
 */
public record RoleResource(
    Long id,
    String name
) {
}
