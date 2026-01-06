package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a username already exists.
 */
public class UsernameAlreadyExistsException extends RuntimeException {

  /**
   * Creates a new {@code UsernameAlreadyExistsException}.
   *
   * @param username username that already exists
   */
  public UsernameAlreadyExistsException(String username) {
    super("Username already exists: " + username);
  }
}
