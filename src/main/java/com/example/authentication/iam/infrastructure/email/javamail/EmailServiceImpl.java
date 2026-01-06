package com.example.authentication.iam.infrastructure.email.javamail;

import com.example.authentication.iam.application.internal.outboundservices.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * JavaMail-based implementation of {@link EmailService}.
 *
 * <p>This service is responsible for sending transactional emails,
 * such as account verification emails.</p>
 */
@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${spring.mail.from}")
  private String fromEmail;

  /**
   * Creates a new {@code EmailServiceImpl}.
   *
   * @param mailSender mail sender used to send emails
   * @param templateEngine Thymeleaf template engine
   */
  public EmailServiceImpl(
      JavaMailSender mailSender,
      TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  /**
   * Sends an account verification email.
   *
   * @param to recipient email address
   * @param verificationLink verification URL
   */
  @Override
  public void sendVerificationEmail(
      String to,
      String verificationLink) {
    try {
      MimeMessage message =
          mailSender.createMimeMessage();

      MimeMessageHelper helper =
                new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

      Context context = new Context();
      context.setVariable(
                "verificationLink",
                verificationLink);

      String html =
                templateEngine.process(
                    "email-verification",
                    context);

      helper.setFrom(fromEmail);
      helper.setTo(to);
      helper.setSubject(
                "Confirm your account - Authentication Service");
      helper.setText(html, true);

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(
                "Error sending verification email",
                e);
    }
  }
}
