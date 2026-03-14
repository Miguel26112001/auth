package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when an email cannot be sent.
 */
public class EmailSendingException extends RuntimeException {

  /**
   * Creates a new {@code EmailSendingException}.
   *
   * @param message error message describing the failure
   * @param cause underlying cause of the error
   */
  public EmailSendingException(String message, Throwable cause) {
    super(message, cause);
  }
}
