package com.example.authentication.iam.application.internal.outboundservices.tokens;

/**
 * Service responsible for generating and validating authentication tokens.
 */
public interface TokenService {

  /**
   * Generates a token for the given username.
   *
   * @param username username to generate the token for
   * @return generated token
   */
  String generateToken(String username);

  /**
   * Extracts the username from the given token.
   *
   * @param token authentication token
   * @return username extracted from the token
   */
  String getUsernameFromToken(String token);

  /**
   * Validates the given token.
   *
   * @param token authentication token
   * @return {@code true} if the token is valid, {@code false} otherwise
   */
  boolean validateToken(String token);
}
