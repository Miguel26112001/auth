package com.example.authentication.iam.application.internal.outboundservices.hashing;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.example.authentication.iam.domain.exceptions.WeakPasswordException;

class PasswordValidatorTest {

  @Test
  @DisplayName("validate - valid password: does not throw exception")
  void validate_validPassword_doesNotThrowException() {
    // Arrange
    var password = "StrongP@ssw0rd";

    // Act
    PasswordValidator.validate(password);
  }

  @Test
  @DisplayName("validate - null password: throws WeakPasswordException")
  void validate_nullPassword_throwsWeakPasswordException() {
    // Arrange
    String password = null;

    // Act & Assert
    assertThrows(WeakPasswordException.class,
        () -> PasswordValidator.validate(password));
  }

  @ParameterizedTest(name = "validate - invalid password \"{0}\" throws WeakPasswordException")
  @ValueSource(strings = {
      "123",              // too short
      "strongp@ssw0rd",   // missing uppercase
      "STRONGP@SSW0RD",   // missing lowercase
      "StrongP@ssword",   // missing number
      "StrongPassw0rd",   // missing special character
      "",                 // empty
      "Strong P@ssw0rd"   // contains spaces
  })
  @DisplayName("validate - invalid passwords: throws WeakPasswordException")
  void validate_invalidPasswords_throwsWeakPasswordException(String password) {

    // Act & Assert
    assertThrows(
        WeakPasswordException.class,
        () -> PasswordValidator.validate(password)
    );
  }
}
