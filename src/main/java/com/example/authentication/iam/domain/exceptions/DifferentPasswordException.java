package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when the provided password does not match
 * the current user's password.
 */
public class DifferentPasswordException extends RuntimeException {

  /**
   * Creates a new {@code DifferentPasswordException}.
   */
  public DifferentPasswordException() {
    super("The password of the user is incorrect.");
  }
}
