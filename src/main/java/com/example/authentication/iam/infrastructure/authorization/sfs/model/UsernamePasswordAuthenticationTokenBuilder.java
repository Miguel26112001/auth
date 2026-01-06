package com.example.authentication.iam.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * Utility class responsible for building {@link UsernamePasswordAuthenticationToken}
 * instances from authenticated user details.
 */
public final class UsernamePasswordAuthenticationTokenBuilder {

  private UsernamePasswordAuthenticationTokenBuilder() {
    // Utility class
  }

  /**
   * Builds an authentication token using the given principal and HTTP request.
   *
   * @param principal authenticated user details
   * @param request HTTP servlet request
   * @return authentication token
   */
  public static UsernamePasswordAuthenticationToken build(
      UserDetails principal,
      HttpServletRequest request) {

    var authenticationToken =
        new UsernamePasswordAuthenticationToken(
            principal,
            null,
            principal.getAuthorities());

    authenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));

    return authenticationToken;
  }
}
