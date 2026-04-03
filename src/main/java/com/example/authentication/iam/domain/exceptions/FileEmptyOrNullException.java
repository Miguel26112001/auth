package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when a file is empty or null during profile image update.
 */
public class FileEmptyOrNullException extends RuntimeException {

  /**
   * Creates a new {@code FileEmptyOrNullException}.
   */
  public FileEmptyOrNullException() {
    super("File is empty or null");
  }

  /**
   * Creates a new {@code FileEmptyOrNullException} with a custom message.
   *
   * @param message the detail message
   */
  public FileEmptyOrNullException(String message) {
    super(message);
  }
}
