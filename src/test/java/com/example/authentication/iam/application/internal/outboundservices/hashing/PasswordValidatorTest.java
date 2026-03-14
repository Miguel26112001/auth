package com.example.authentication.iam.application.internal.outboundservices.hashing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.authentication.iam.domain.exceptions.WeakPasswordException;

class PasswordValidatorTest {

  @Test
  @DisplayName("validate - valid password: does not throw exception")
  void validate_validPassword_doesNotThrowException() {
    // Arrange
    var password = "StrongP@ssw0rd";

    // Act & Assert
    PasswordValidator.validate(password);
  }

  @Test
  @DisplayName("validate - null password: throws WeakPasswordException")
  void validate_nullPassword_throwsWeakPasswordException() {
    // Arrange
    String password = null;

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - short password: throws WeakPasswordException")
  void validate_shortPassword_throwsWeakPasswordException() {
    // Arrange
    var password = "123";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - missing uppercase: throws WeakPasswordException")
  void validate_missingUppercase_throwsWeakPasswordException() {
    // Arrange
    var password = "strongp@ssw0rd";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - missing lowercase: throws WeakPasswordException")
  void validate_missingLowercase_throwsWeakPasswordException() {
    // Arrange
    var password = "STRONGP@SSW0RD";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - missing number: throws WeakPasswordException")
  void validate_missingNumber_throwsWeakPasswordException() {
    // Arrange
    var password = "StrongP@ssword";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - missing special character: throws WeakPasswordException")
  void validate_missingSpecialCharacter_throwsWeakPasswordException() {
    // Arrange
    var password = "StrongPassw0rd";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - empty password: throws WeakPasswordException")
  void validate_emptyPassword_throwsWeakPasswordException() {
    // Arrange
    var password = "";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }

  @Test
  @DisplayName("validate - password with spaces: throws WeakPasswordException")
  void validate_passwordWithSpaces_throwsWeakPasswordException() {
    // Arrange
    var password = "Strong P@ssw0rd";

    // Act & Assert
    assertThrows(WeakPasswordException.class, () -> PasswordValidator.validate(password));
  }
}
