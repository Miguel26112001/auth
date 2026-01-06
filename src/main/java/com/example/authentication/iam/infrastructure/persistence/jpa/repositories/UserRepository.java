package com.example.authentication.iam.infrastructure.persistence.jpa.repositories;

import com.example.authentication.iam.domain.model.aggregates.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link User} aggregate roots.
 *
 * <p>Provides query methods to retrieve and validate users by username and email.</p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds a user by its username.
   *
   * @param username unique username
   * @return optional user
   */
  Optional<User> findByUsername(String username);

  /**
   * Checks whether a user with the given username exists.
   *
   * @param username username to check
   * @return {@code true} if the user exists, {@code false} otherwise
   */
  boolean existsByUsername(String username);

  /**
   * Checks whether a user with the given email exists.
   *
   * @param email email address to check
   * @return {@code true} if the user exists, {@code false} otherwise
   */
  boolean existsByEmail(String email);
}
