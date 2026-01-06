package com.example.authentication.iam.infrastructure.authorization.sfs.pipeline;

import com.example.authentication.iam.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import com.example.authentication.iam.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Spring Security filter that processes incoming requests and performs
 * bearer token authentication using JWT.
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);

  private final BearerTokenService tokenService;

  @Qualifier("defaultUserDetailsService")
  private final UserDetailsService userDetailsService;

  /**
   * Creates a new bearer authorization request filter.
   *
   * @param tokenService JWT bearer token service
   * @param userDetailsService user details service
   */
  public BearerAuthorizationRequestFilter(
      BearerTokenService tokenService,
      UserDetailsService userDetailsService) {

    this.tokenService = tokenService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String token = tokenService.getBearerTokenFrom(request);
      LOGGER.debug("Bearer token received");

      if (token != null && tokenService.validateToken(token)) {
        String username = tokenService.getUsernameFromToken(token);
        var userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails.isEnabled()) {
          SecurityContextHolder.getContext().setAuthentication(
              UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
        } else {
          LOGGER.warn(
              "User {} is disabled but attempted to authenticate with a valid token",
              username);
          SecurityContextHolder.clearContext();
        }
      }
    } catch (Exception exception) {
      LOGGER.error(
          "Failed to set user authentication: {}",
          exception.getMessage());
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
  }
}
