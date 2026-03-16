package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a user account is not verified.
 */
public class UserNotVerifiedException extends RuntimeException {

  /**
   * Creates a new {@code UserNotVerifiedException}.
   *
   * @param username username of the inactive user
   */
  public UserNotVerifiedException(String username) {
    super("User is not verified: " + username);
  }
}
