package com.example.authentication.iam.domain.model.queries;

/**
 * Query used to retrieve a user by its unique identifier.
 *
 * @param userId unique identifier of the user
 */
public record GetUserByIdQuery(
    Long userId) {
}
