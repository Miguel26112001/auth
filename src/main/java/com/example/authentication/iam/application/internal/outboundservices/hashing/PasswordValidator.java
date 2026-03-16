package com.example.authentication.iam.application.internal.outboundservices.hashing;

import com.example.authentication.iam.domain.exceptions.WeakPasswordException;
import java.util.regex.Pattern;

/**
 * Utility class responsible for validating password strength.
 */
public final class PasswordValidator {

  private static final String STRONG_PASSWORD_REGEX =
      "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

  private static final Pattern STRONG_PASSWORD_PATTERN =
      Pattern.compile(STRONG_PASSWORD_REGEX);

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
    if (password == null || !STRONG_PASSWORD_PATTERN.matcher(password).matches()) {
      throw new WeakPasswordException(
          "Password must be at least 8 characters long, "
              + "include at least one uppercase letter, "
              + "one lowercase letter, one number, "
              + "and one special character.");
    }
  }
}