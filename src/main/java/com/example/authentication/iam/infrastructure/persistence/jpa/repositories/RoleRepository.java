package com.example.authentication.iam.infrastructure.persistence.jpa.repositories;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for {@link Role} entities.
 *
 * <p>Provides methods to query roles by their {@link Roles} value object.</p>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  /**
   * Finds a role by its enum value.
   *
   * @param roles role enum
   * @return optional role
   */
  Optional<Role> findByRoles(Roles roles);

  /**
   * Checks whether a role with the given enum value exists.
   *
   * @param roles role enum
   * @return {@code true} if the role exists, {@code false} otherwise
   */
  boolean existsByRoles(Roles roles);
}
