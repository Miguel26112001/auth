package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import java.util.List;

/**
 * Assembles a {@link UserResource} from a {@link User} entity.
 */
public class UserResourceFromEntityAssembler {

  /**
   * Converts a {@link User} entity into a {@link UserResource}.
   *
   * @param user the user entity
   * @return the corresponding UserResource
   */
  public static UserResource toResourceFromEntity(User user) {

    List<String> roles = user.getRoles().stream()
        .map(Role::getStringRole)
        .toList();

    return new UserResource(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        roles,
        user.isActive()
    );
  }
}
