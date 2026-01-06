package com.example.authentication.iam.application.internal.queryservices;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.domain.services.RoleQueryService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Query service implementation responsible for retrieving role information.
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {

  private final RoleRepository roleRepository;

  public RoleQueryServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Retrieves all available roles.
   *
   * @param query query to retrieve all roles
   * @return list of roles
   */
  @Override
  public List<Role> handle(GetAllRolesQuery query) {
    return roleRepository.findAll();
  }

  /**
   * Retrieves a role by its name.
   *
   * @param query query containing the role name
   * @return optional role if found, otherwise empty
   */
  @Override
  public Optional<Role> handle(GetRoleByNameQuery query) {
    try {
      var roleName = query.roles().toUpperCase();
      if (!roleName.startsWith("ROLE_")) {
        roleName = "ROLE_" + roleName;
      }

      var roles = Roles.valueOf(roleName);
      return roleRepository.findByRoles(roles);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}
