package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembles an {@link AuthenticatedUserResource} from a {@link User} entity.
 */
public class AuthenticatedUserResourceFromEntityAssembler {

  /**
   * Converts a {@link User} entity and a token into an {@link AuthenticatedUserResource}.
   *
   * @param user  the user entity to convert
   * @param token the authentication token for the user
   * @return the corresponding AuthenticatedUserResource
   */
  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    return new AuthenticatedUserResource(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        token
    );
  }
}
