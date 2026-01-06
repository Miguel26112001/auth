package com.example.authentication.iam.domain.model.queries;

/**
 * Query used to retrieve a user by its username.
 *
 * @param username unique username of the user
 */
public record GetUserByUsernameQuery(
    String username) {
}
