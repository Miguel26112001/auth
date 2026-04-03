package com.example.authentication.iam.domain.exceptions;

/**
 * Exception thrown when image upload to Cloudinary fails.
 */
public class ImageUploadException extends RuntimeException {

  /**
   * Creates a new {@code ImageUploadException}.
   */
  public ImageUploadException() {
    super("Error uploading image to Cloudinary");
  }

  /**
   * Creates a new {@code ImageUploadException} with a custom message.
   *
   * @param message the detail message
   */
  public ImageUploadException(String message) {
    super(message);
  }

  /**
   * Creates a new {@code ImageUploadException} with a custom message and cause.
   *
   * @param message the detail message
   * @param cause the cause
   */
  public ImageUploadException(String message, Throwable cause) {
    super(message, cause);
  }
}
