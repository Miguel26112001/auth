package com.example.authentication.iam.interfaces.rest.exceptions;

import com.example.authentication.iam.domain.exceptions.EmailAlreadyExistsException;
import com.example.authentication.iam.domain.exceptions.InvalidPasswordException;
import com.example.authentication.iam.domain.exceptions.RoleNotFoundException;
import com.example.authentication.iam.domain.exceptions.UserNotActiveException;
import com.example.authentication.iam.domain.exceptions.UserNotFoundException;
import com.example.authentication.iam.domain.exceptions.UsernameAlreadyExistsException;
import com.example.authentication.iam.domain.exceptions.WeakPasswordException;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles domain exceptions and maps them to appropriate HTTP responses.
 */
@RestControllerAdvice
public class DomainExceptionHandler {

  /**
   * Handles RoleNotFoundException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 404 and error message
   */
  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<MessageResource> handleRoleNotFound(RoleNotFoundException e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles UsernameAlreadyExistsException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 409 and error message
   */
  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<MessageResource> handleUsernameAlreadyExistsException(
      UsernameAlreadyExistsException e
  ) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles EmailAlreadyExistsException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 409 and error message
   */
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<MessageResource> handleEmailAlreadyExistsException(
      EmailAlreadyExistsException e
  ) {
    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles UserNotFoundException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 404 and error message
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<MessageResource> handleUserNotFoundException(
      UserNotFoundException e
  ) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles InvalidPasswordException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 400 and error message
   */
  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<MessageResource> handleInvalidPasswordException(
      InvalidPasswordException e
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles UserNotActiveException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 403 and error message
   */
  @ExceptionHandler(UserNotActiveException.class)
  public ResponseEntity<MessageResource> handleUserNotActiveException(
      UserNotActiveException e
  ) {
    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(
            new MessageResource(e.getMessage())
        );
  }

  /**
   * Handles WeakPasswordException.
   *
   * @param e the exception
   * @return ResponseEntity with HTTP 400 and error message
   */
  @ExceptionHandler(WeakPasswordException.class)
  public ResponseEntity<MessageResource> handleWeakPasswordException(
      WeakPasswordException e
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            new MessageResource(e.getMessage())
        );
  }
}
