package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when attempting to register an email
 * address that already exists in the system.
 */
public class EmailAlreadyExistsException extends RuntimeException {

  /**
   * Creates a new {@code EmailAlreadyExistsException}.
   *
   * @param email email address that already exists
   */
  public EmailAlreadyExistsException(String email) {
    super("Email already exists: " + email);
  }
}
