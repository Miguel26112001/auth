package com.example.authentication.iam.application.internal.commandservices;

import com.example.authentication.iam.application.internal.outboundservices.email.EmailService;
import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.authentication.iam.application.internal.outboundservices.hashing.PasswordValidator;
import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.authentication.iam.domain.exceptions.*;
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.*;
import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final TokenService tokenService;
  private final RoleRepository roleRepository;
  private final EmailService emailService;

  public UserCommandServiceImpl(
      UserRepository userRepository,
      HashingService hashingService,
      TokenService tokenService,
      RoleRepository roleRepository,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.hashingService = hashingService;
    this.tokenService = tokenService;
    this.roleRepository = roleRepository;
    this.emailService = emailService;
  }

  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var user = userRepository.findByUsername(command.username())
        .orElseThrow(() -> new UserNotFoundException(command.username()));

    if (!hashingService.matches(command.password(), user.getHashedPassword())) {
      throw new InvalidPasswordException();
    }

    if (!user.isActive()) {
      throw new UserNotActiveException(user.getUsername());
    }

    var token = tokenService.generateToken(user.getUsername());
    return Optional.of(ImmutablePair.of(user, token));
  }

  @Override
  public Optional<User> handle(SignUpCommand command) {
    if (userRepository.existsByUsername(command.username())) {
      throw new UsernameAlreadyExistsException(command.username());
    }

    if (userRepository.existsByEmail(command.email())) {
      throw new EmailAlreadyExistsException(command.email());
    }

    PasswordValidator.validate(command.password());

    var roles = command.roles().stream().map(role ->
      roleRepository.findByRoles(role.getRoles())
          .orElseThrow(() -> new RoleNotFoundException(role.getRoles().name())))
        .toList();

    var user = new User(command.username(), command.email(), hashingService.encode(command.password()), roles);
    userRepository.save(user);

    var token = tokenService.generateToken(user.getUsername());
    var verificationLink = command.baseUrl() + "/api/v1/authentication/verify?token=" + token;
    emailService.sendVerificationEmail(user.getEmail(), verificationLink);

    return userRepository.findByUsername(command.username());
  }

  @Override
  public Optional<User> handle(VerifyUserCommand command) {
    if (!tokenService.validateToken(command.token())) {
      throw new RuntimeException("Invalid token");
    }
    var username = tokenService.getUsernameFromToken(command.token());
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    user.setVerified(true);
    userRepository.save(user);
    return Optional.of(user);
  }

  @Override
  public Optional<User> handle(UpdateUserStatusCommand command) {
    var user = userRepository.findById(command.userId())
        .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));
    user.setActive(command.isActive());
    userRepository.save(user);
    return Optional.of(user);
  }

  @Override
  public Optional<User> handle(UpdatePasswordCommand command) {
    var user = userRepository.findById(command.userId())
        .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));

    if (!hashingService.matches(command.currentPassword(), user.getHashedPassword())) {
      throw new DifferentPasswordException();
    }

    PasswordValidator.validate(command.newPassword());
    user.setHashedPassword(hashingService.encode(command.newPassword()));
    userRepository.save(user);
    return Optional.of(user);
  }
}
