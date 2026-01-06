package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a provided password is invalid.
 */
public class InvalidPasswordException extends RuntimeException {

  /**
   * Creates a new {@code InvalidPasswordException}.
   */
  public InvalidPasswordException() {
    super("Invalid password");
  }
}
