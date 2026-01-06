package com.example.authentication.iam.domain.model.queries;

/**
 * Query used to retrieve a role by its name.
 *
 * @param roles role name to be searched
 */
public record GetRoleByNameQuery(
    String roles) {
}
