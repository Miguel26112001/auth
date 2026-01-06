package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.model.commands.UpdatePasswordCommand;
import com.example.authentication.iam.domain.model.commands.UpdateUserStatusCommand;
import com.example.authentication.iam.domain.model.commands.VerifyUserCommand;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Command service responsible for handling user-related write operations.
 */
public interface UserCommandService {

  /**
   * Handles the user sign-in process.
   *
   * @param command sign-in command
   * @return an optional pair containing the authenticated user and a token
   */
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);

  /**
   * Handles the user sign-up process.
   *
   * @param command sign-up command
   * @return optional created user
   */
  Optional<User> handle(SignUpCommand command);

  /**
   * Updates the active status of a user.
   *
   * @param command update user status command
   * @return optional updated user
   */
  Optional<User> handle(UpdateUserStatusCommand command);

  /**
   * Updates the password of a user.
   *
   * @param command update password command
   * @return optional updated user
   */
  Optional<User> handle(UpdatePasswordCommand command);

  /**
   * Verifies a user using a verification token.
   *
   * @param command verify user command
   * @return optional verified user
   */
  Optional<User> handle(VerifyUserCommand command);
}
