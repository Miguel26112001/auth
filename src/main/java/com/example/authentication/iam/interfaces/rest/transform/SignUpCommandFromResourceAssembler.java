package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.SignUpResource;

/**
 * Assembles a {@link SignUpCommand} from a {@link SignUpResource}.
 */
public class SignUpCommandFromResourceAssembler {

  /**
   * Converts a {@link SignUpResource} into a {@link SignUpCommand}.
   *
   * <p>Maps role names to {@link Role} objects and validates them before creating the command.</p>
   *
   * @param resource the sign-up resource containing user info
   * @param baseUrl  the base URL for email confirmation links
   * @return the corresponding SignUpCommand
   */
  public static SignUpCommand toCommandFromResource(SignUpResource resource, String baseUrl) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(Role::toRoleFromName).toList()
        : null;

    var validatedRoles = Role.validateRoleSet(roles);

    return new SignUpCommand(
        resource.username(),
        resource.email(),
        resource.password(),
        validatedRoles,
        baseUrl
    );
  }
}
