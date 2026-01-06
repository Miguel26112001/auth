package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a requested role cannot be found.
 */
public class RoleNotFoundException extends RuntimeException {

  /**
   * Creates a new {@code RoleNotFoundException}.
   *
   * @param roleName name of the role that was not found
   */
  public RoleNotFoundException(String roleName) {
    super("Role not found: " + roleName);
  }
}
