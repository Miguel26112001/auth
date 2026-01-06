package com.example.authentication.shared.interfaces.rest.exceptions;

import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 *
 * <p>Handles validation errors, transaction errors, runtime exceptions,
 * and general uncaught exceptions.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles validation errors from @Valid annotated request bodies.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<MessageResource> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    String errorMessage = e.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new MessageResource(errorMessage));
  }

  /**
   * Handles validation errors from constraint violations (e.g., @NotNull, @Email).
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<MessageResource> handleConstraintViolationException(
      ConstraintViolationException e) {

    String errorMessage = e.getConstraintViolations().stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
        .collect(Collectors.joining(", "));

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new MessageResource(errorMessage));
  }

  /**
   * Handles transaction system exceptions and unwraps constraint violations.
   */
  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<MessageResource> handleTransactionSystemException(
      TransactionSystemException e) {

    Throwable cause = e.getRootCause();
    if (cause instanceof ConstraintViolationException constraintViolationException) {
      return handleConstraintViolationException(constraintViolationException);
    }
    return handleInternalServerError(e);
  }

  /**
   * Handles all runtime exceptions.
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<MessageResource> handleRuntimeException(RuntimeException e) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new MessageResource(e.getMessage()));
  }

  /**
   * Handles all other uncaught exceptions.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<MessageResource> handleInternalServerError(Exception e) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new MessageResource("An unexpected error occurred."));
  }
}
