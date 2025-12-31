package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.*;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
  Optional<User> handle(UpdateUserStatusCommand command);
  Optional<User> handle(UpdatePasswordCommand command);
  Optional<User> handle(VerifyUserCommand command);
}
