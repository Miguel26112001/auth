package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a verification token is invalid.
 */
public class InvalidTokenException extends RuntimeException {

  /**
   * Creates a new {@code InvalidTokenException}.
   */
  public InvalidTokenException() {
    super("Invalid verification token");
  }
}
