package com.example.authentication.iam.application.internal.outboundservices.email;

/**
 * Service responsible for sending emails related to user operations.
 */
public interface EmailService {
  /**
   * Sends a verification email to the given recipient.
   *
   * @param to email recipient
   * @param verificationLink verification link to be sent
   */
  void sendVerificationEmail(String to, String verificationLink);
}
