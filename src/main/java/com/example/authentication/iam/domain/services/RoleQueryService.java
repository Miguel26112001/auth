package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;
import java.util.List;
import java.util.Optional;

/**
 * Query service responsible for role-related read operations.
 */
public interface RoleQueryService {

  /**
   * Retrieves all roles available in the system.
   *
   * @param query query to retrieve all roles
   * @return list of roles
   */
  List<Role> handle(GetAllRolesQuery query);

  /**
   * Retrieves a role by its name.
   *
   * @param query query containing the role name
   * @return optional role if found, otherwise empty
   */
  Optional<Role> handle(GetRoleByNameQuery query);
}
