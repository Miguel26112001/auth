package com.example.authentication.iam.application.internal.outboundservices.hashing;

import com.example.authentication.iam.domain.exceptions.WeakPasswordException;
import java.util.regex.Pattern;

/**
 * Utility class responsible for validating password strength.
 */
public final class PasswordValidator {

  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

  private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

  private PasswordValidator() {
      // Utility class
  }

  /**
   * Validates the given password against strength requirements.
   *
   * @param password password to validate
   * @throws WeakPasswordException if the password does not meet the requirements
   */
  public static void validate(String password) {
    if (password == null || !PATTERN.matcher(password).matches()) {
      throw new WeakPasswordException(
          "Password must be at least 8 characters long, "
              + "include at least one uppercase letter, "
              + "one lowercase letter, one number, "
              + "and one special character.");
    }
  }
}
