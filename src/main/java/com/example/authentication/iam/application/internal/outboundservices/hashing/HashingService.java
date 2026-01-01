package com.example.authentication.iam.application.internal.outboundservices.hashing;

/**
 * Service responsible for password hashing and verification.
 */
public interface HashingService {

  /**
   * Encodes the given raw password.
   *
   * @param rawPassword raw password to encode
   * @return encoded password
   */
  String encode(CharSequence rawPassword);

  /**
   * Verifies whether a raw password matches the encoded password.
   *
   * @param rawPassword raw password
   * @param encodedPassword encoded password
   * @return {@code true} if the passwords match, {@code false} otherwise
   */
  boolean matches(CharSequence rawPassword, String encodedPassword);
}