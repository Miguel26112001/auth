package com.example.authentication.iam.application.internal.commandservices;

import java.util.Collections;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.authentication.iam.application.internal.outboundservices.email.EmailService;
import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.authentication.iam.domain.exceptions.InvalidPasswordException;
import com.example.authentication.iam.domain.exceptions.UserNotActiveException;
import com.example.authentication.iam.domain.exceptions.UserNotFoundException;
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserCommandServiceImplTest {
  @Mock
  UserRepository userRepository;
  @Mock
  HashingService hashingService;
  @Mock
  TokenService tokenService;
  @Mock
  RoleRepository roleRepository;
  @Mock
  EmailService emailService;
  @InjectMocks
  UserCommandServiceImpl service;

  @Test
  @DisplayName("handle(SignInCommand) - success: returns user and token")
  void handle_signIn_success_returnsUserAndToken() {
    // Arrange
    var username = "alice";
    var rawPassword = "password";
    var hashed = "hashedPassword";
    var token = "token-abc";
    User user = new User(username, "alice@example.com", hashed, Collections.emptyList());
    user.setActive(true);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(hashingService.matches(rawPassword, hashed)).thenReturn(true);
    when(tokenService.generateToken(username)).thenReturn(token);

    var command = new SignInCommand(username, rawPassword);

    // Act
    Optional<ImmutablePair<User, String>> result = service.handle(command);

    // Assert
    assertThat(result).isPresent();
    ImmutablePair<User, String> pair = result.get();
    assertThat(pair.getLeft()).isSameAs(user);
    assertThat(pair.getRight()).isEqualTo(token);
  }

  @Test
  @DisplayName("handle(SignInCommand) - user not found: throws UserNotFoundException")
  void handle_signIn_userNotFound_throwsUserNotFoundException() {
    // Arrange
    var username = "missing";
    var rawPassword = "irrelevant";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    var command = new SignInCommand(username, rawPassword);

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> service.handle(command));
  }

  @Test
  @DisplayName("handle(SignInCommand) - invalid password: throws InvalidPasswordException")
  void handle_signIn_invalidPassword_throwsInvalidPasswordException() {
    // Arrange
    var username = "bob";
    var rawPassword = "wrong";
    var hashed = "hashed";
    User user = new User(username, "bob@example.com", hashed, Collections.emptyList());
    user.setActive(true);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(hashingService.matches(rawPassword, hashed)).thenReturn(false);

    var command = new SignInCommand(username, rawPassword);

    // Act & Assert
    assertThrows(InvalidPasswordException.class, () -> service.handle(command));
  }

  @Test
  @DisplayName("handle(SignInCommand) - user not active: throws UserNotActiveException")
  void handle_signIn_userNotActive_throwsUserNotActiveException() {
    // Arrange
    var username = "charlie";
    var rawPassword = "password";
    var hashed = "hashed";
    User user = new User(username, "charlie@example.com", hashed, Collections.emptyList());
    user.setActive(false);

    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    when(hashingService.matches(rawPassword, hashed)).thenReturn(true);

    var command = new SignInCommand(username, rawPassword);

    // Act & Assert
    assertThrows(UserNotActiveException.class, () -> service.handle(command));
  }
}
