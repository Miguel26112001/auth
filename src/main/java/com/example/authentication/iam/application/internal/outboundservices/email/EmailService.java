package com.example.authentication.iam.application.internal.outboundservices.email;

public interface EmailService {
    void sendVerificationEmail(String to, String verificationLink);
}
