package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a user cannot be found.
 */
public class UserNotFoundException extends RuntimeException {

  /**
   * Creates a new {@code UserNotFoundException}.
   *
   * @param username username of the user that was not found
   */
  public UserNotFoundException(String username) {
    super("User not found: " + username);
  }
}
