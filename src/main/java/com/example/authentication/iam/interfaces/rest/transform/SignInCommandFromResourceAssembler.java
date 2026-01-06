package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.interfaces.rest.resources.SignInResource;

/**
 * Assembles a {@link SignInCommand} from a {@link SignInResource}.
 */
public class SignInCommandFromResourceAssembler {

  /**
   * Converts a {@link SignInResource} into a {@link SignInCommand}.
   *
   * @param signInResource the resource containing sign-in data
   * @return the corresponding SignInCommand
   */
  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(
        signInResource.username(),
        signInResource.password()
    );
  }
}
