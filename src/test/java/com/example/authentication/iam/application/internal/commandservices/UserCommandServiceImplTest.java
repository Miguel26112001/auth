package com.example.authentication.iam.application.internal.commandservices;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.authentication.iam.application.internal.outboundservices.email.EmailService;
import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.authentication.iam.application.internal.outboundservices.hashing.PasswordValidator;
import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.authentication.iam.domain.exceptions.*;
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.model.commands.UpdateUserStatusCommand;
import com.example.authentication.iam.domain.model.commands.VerifyUserCommand;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceImplTest {
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

  @Test
  @DisplayName("handle(SignUpCommand) - success: creates user and sends verification email")
  void handle_signUp_success_createsUserAndSendsVerificationEmail() {
    // Arrange
    var username = "newuser";
    var email = "newuser@example.com";
    var rawPassword = "StrongP@ssw0rd";
    var hashed = "hashedPwd";
    var token = "signup-token-1";
    var baseUrl = "http://localhost:8080";
    User user = new User(username, email, hashed, Collections.emptyList());

    when(userRepository.existsByUsername(username)).thenReturn(false);
    when(userRepository.existsByEmail(email)).thenReturn(false);
    when(hashingService.encode(rawPassword)).thenReturn(hashed);
    when(tokenService.generateToken(username)).thenReturn(token);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    var command = new SignUpCommand(username, email, rawPassword, Collections.emptyList(), baseUrl);

    // Act
    Optional<User> result = service.handle(command);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(user);
    var expectedLink = baseUrl + "/api/v1/authentication/verify?token=" + token;
    verify(emailService).sendVerificationEmail(email, expectedLink);
  }

  @Test
  @DisplayName("handle(SignUpCommand) - username exists: throws UsernameAlreadyExistsException")
  void handle_signUp_usernameExists_throwsUsernameAlreadyExistsException() {
    // Arrange
    var username = "existing";
    var email = "some@example.com";
    var rawPassword = "password";
    var baseUrl = "http://localhost";
    when(userRepository.existsByUsername(username)).thenReturn(true);
    var command = new SignUpCommand(username, email, rawPassword, Collections.emptyList(), baseUrl);

    // Act & Assert
    assertThrows(UsernameAlreadyExistsException.class, () -> service.handle(command));
  }

  @Test
  @DisplayName("handle(SignUpCommand) - email exists: throws EmailAlreadyExistsException")
  void handle_signUp_emailExists_throwsEmailAlreadyExistsException() {
    // Arrange
    var username = "unique";
    var email = "existing@example.com";
    var rawPassword = "password";
    var baseUrl = "http://localhost";
    when(userRepository.existsByUsername(username)).thenReturn(false);
    when(userRepository.existsByEmail(email)).thenReturn(true);
    var command = new SignUpCommand(username, email, rawPassword, Collections.emptyList(), baseUrl);

    // Act & Assert
    assertThrows(EmailAlreadyExistsException.class, () -> service.handle(command));
  }

  @Test
  @DisplayName("handle(SignUpCommand) - role not found: throws RoleNotFoundException")
  void handle_signUp_roleNotFound_throwsRoleNotFoundException() {
    // Arrange
    var username = "user-with-missing-role";
    var email = "no-role@example.com";
    var rawPassword = "StrongP@ssw0rd";
    var baseUrl = "http://localhost";

    when(userRepository.existsByUsername(username)).thenReturn(false);
    when(userRepository.existsByEmail(email)).thenReturn(false);

    var requestedRole = mock(Role.class);
    when(requestedRole.getRoles()).thenReturn(Roles.ROLE_USER);

    when(roleRepository.findByRoles(
        Roles.ROLE_USER))
        .thenReturn(Optional.empty());

    var command = new SignUpCommand(
        username,
        email,
        rawPassword,
        List.of(requestedRole),
        baseUrl
    );

    // Act & Assert
    assertThrows(
        RoleNotFoundException.class,
        () -> service.handle(command)
    );

    // Verificaciones adicionales (alineadas con el resto de tests)
    verify(userRepository, never()).save(any());
    verify(emailService, never()).sendVerificationEmail(any(), any());
  }

  @Test
  @DisplayName("handle(SignUpCommand) - invalid password: throws InvalidPasswordException")
  void handle_signUp_invalidPassword_throwsInvalidPasswordException() {
    // Arrange
    var username = "weak-pass-user";
    var email = "weak@example.com";
    var weakPassword = "123";
    var baseUrl = "http://localhost";

    when(userRepository.existsByUsername(username)).thenReturn(false);
    when(userRepository.existsByEmail(email)).thenReturn(false);

    var command = new SignUpCommand(
        username,
        email,
        weakPassword,
        java.util.Collections.emptyList(),
        baseUrl
    );

    try (MockedStatic<PasswordValidator> mocked =
             mockStatic(PasswordValidator.class)) {

      mocked.when(() ->
          PasswordValidator
              .validate(weakPassword)
      ).thenThrow(new InvalidPasswordException());

      // Act & Assert
      assertThrows(
          InvalidPasswordException.class,
          () -> service.handle(command)
      );
    }

    // Verificaciones de seguridad (alineadas con el resto de tests)
    verify(userRepository, never()).save(any());
    verify(emailService, never()).sendVerificationEmail(any(), any());
  }

  @Test
  @DisplayName("handle(VerifyUserCommand) - success: verifies user and returns updated user")
  void handle_verifyUser_success_verifiesUserAndReturnsUpdatedUser() {
    // Arrange
    var token = "valid-token";
    var username = "alice";
    var hashed = "hashedPassword";
    User user = new User(username, "alice@example.com", hashed, Collections.emptyList());
    user.setActive(true);
    user.setVerified(false);

    when(tokenService.validateToken(token)).thenReturn(true);
    when(tokenService.getUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    var command = new VerifyUserCommand(token);

    // Act
    Optional<User> result = service.handle(command);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(user);
    assertThat(user.isVerified()).isTrue();
    verify(userRepository).save(user);
  }

  @Test
  @DisplayName("handle(VerifyUserCommand) - invalid token: throws RuntimeException")
  void handle_verifyUser_invalidToken_throwsRuntimeException() {
    // Arrange
    var token = "invalid-token";

    when(tokenService.validateToken(token)).thenReturn(false);

    var command = new VerifyUserCommand(token);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> service.handle(command), "Invalid token");
  }

  @Test
  @DisplayName("handle(VerifyUserCommand) - user not found: throws UserNotFoundException")
  void handle_verifyUser_userNotFound_throwsUserNotFoundException() {
    // Arrange
    var token = "valid-token";
    var username = "missing";

    when(tokenService.validateToken(token)).thenReturn(true);
    when(tokenService.getUsernameFromToken(token)).thenReturn(username);
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    var command = new VerifyUserCommand(token);

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> service.handle(command));
  }

  @Test
  @DisplayName("handle(UpdateUserStatusCommand) - success: updates user status and returns updated user")
  void handle_updateUserStatus_success_updatesUserStatusAndReturnsUpdatedUser() {
    // Arrange
    var userId = 1L;
    var isActive = true;
    var username = "alice";
    var hashed = "hashedPassword";
    User user = new User(username, "alice@example.com", hashed, Collections.emptyList());
    user.setActive(false); // Initial state

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    var command = new UpdateUserStatusCommand(userId, isActive);

    // Act
    Optional<User> result = service.handle(command);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(user);
    assertThat(user.isActive()).isTrue();
    verify(userRepository).save(user);
  }

  @Test
  @DisplayName("handle(UpdateUserStatusCommand) - user not found: throws UserNotFoundException")
  void handle_updateUserStatus_userNotFound_throwsUserNotFoundException() {
    // Arrange
    var userId = 999L;
    var isActive = false;

    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    var command = new UpdateUserStatusCommand(userId, isActive);

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> service.handle(command));
  }
}
