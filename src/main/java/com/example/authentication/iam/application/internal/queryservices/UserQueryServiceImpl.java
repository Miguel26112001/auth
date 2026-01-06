package com.example.authentication.iam.application.internal.queryservices;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.queries.GetAllUsersQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByIdQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByUsernameQuery;
import com.example.authentication.iam.domain.services.UserQueryService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Query service implementation responsible for retrieving user information.
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

  private final UserRepository userRepository;

  public UserQueryServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all users.
   *
   * @param query query to retrieve all users
   * @return list of users
   */
  @Override
  public List<User> handle(GetAllUsersQuery query) {
    return userRepository.findAll();
  }

  /**
   * Retrieves a user by its identifier.
   *
   * @param query query containing the user identifier
   * @return optional user if found
   */
  @Override
  public Optional<User> handle(GetUserByIdQuery query) {
    return userRepository.findById(query.userId());
  }

  /**
   * Retrieves a user by username.
   *
   * @param query query containing the username
   * @return optional user if found
   */
  @Override
  public Optional<User> handle(GetUserByUsernameQuery query) {
    return userRepository.findByUsername(query.username());
  }
}
