package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.UpdatePasswordCommand;
import com.example.authentication.iam.interfaces.rest.resources.UpdatePasswordResource;

/**
 * Assembles a {@link UpdatePasswordCommand} from a {@link UpdatePasswordResource}.
 */
public class UpdatePasswordCommandFromResourceAssembler {

  /**
   * Converts an {@link UpdatePasswordResource} into an {@link UpdatePasswordCommand}.
   *
   * @param userId  the ID of the user whose password is being updated
   * @param resource the resource containing current and new password
   * @return the corresponding UpdatePasswordCommand
   */
  public static UpdatePasswordCommand toCommandFromResource(
      Long userId,
      UpdatePasswordResource resource) {

    return new UpdatePasswordCommand(
        userId,
        resource.currentPassword(),
        resource.newPassword()
    );
  }
}
