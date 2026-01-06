package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a password does not meet security requirements.
 */
public class WeakPasswordException extends RuntimeException {

  /**
   * Creates a new {@code WeakPasswordException}.
   *
   * @param message detailed explanation of the password weakness
   */
  public WeakPasswordException(String message) {
    super(message);
  }
}
