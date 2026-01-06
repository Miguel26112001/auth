package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a user account is not active.
 */
public class UserNotActiveException extends RuntimeException {

  /**
   * Creates a new {@code UserNotActiveException}.
   *
   * @param username username of the inactive user
   */
  public UserNotActiveException(String username) {
    super("User is not active: " + username);
  }
}
