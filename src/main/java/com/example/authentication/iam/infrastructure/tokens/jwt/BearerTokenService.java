package com.example.authentication.iam.infrastructure.tokens.jwt;

import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Service responsible for extracting Bearer tokens from HTTP requests.
 *
 * <p>This interface extends {@link TokenService} and provides JWT-specific
 * token retrieval logic.</p>
 */
public interface BearerTokenService extends TokenService {

  /**
   * Extracts the Bearer token from the given HTTP request.
   *
   * @param request HTTP servlet request containing the Authorization header
   * @return the extracted Bearer token, or {@code null} if not present
   */
  String getBearerTokenFrom(HttpServletRequest request);
}
