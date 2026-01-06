package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.queries.GetAllUsersQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByIdQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByUsernameQuery;
import java.util.List;
import java.util.Optional;

/**
 * Query service responsible for handling user-related read operations.
 */
public interface UserQueryService {

  /**
   * Retrieves all users.
   *
   * @param query query to retrieve all users
   * @return list of users
   */
  List<User> handle(GetAllUsersQuery query);

  /**
   * Retrieves a user by its identifier.
   *
   * @param query query containing the user identifier
   * @return optional user if found
   */
  Optional<User> handle(GetUserByIdQuery query);

  /**
   * Retrieves a user by its username.
   *
   * @param query query containing the username
   * @return optional user if found
   */
  Optional<User> handle(GetUserByUsernameQuery query);
}
