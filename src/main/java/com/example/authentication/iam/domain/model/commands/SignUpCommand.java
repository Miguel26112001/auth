package com.example.authentication.iam.domain.model.commands;

import com.example.authentication.iam.domain.model.entities.Role;
import java.util.List;

/**
 * Command used to register a new user.
 */
public record SignUpCommand(
    String username,
    String email,
    String password,
    List<Role> roles,
    String baseUrl) {
}
