package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.RoleResource;

/**
 * Assembles a {@link RoleResource} from a {@link Role} entity.
 */
public class RoleResourceFromEntityAssembler {

  /**
   * Converts a {@link Role} entity into a {@link RoleResource}.
   *
   * @param role the role entity to convert
   * @return the corresponding RoleResource
   */
  public static RoleResource toResourceFromEntity(Role role) {
    return new RoleResource(
        role.getId(),
        role.getStringRole()
    );
  }
}
