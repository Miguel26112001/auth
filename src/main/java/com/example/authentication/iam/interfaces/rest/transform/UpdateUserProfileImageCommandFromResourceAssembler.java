package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.UpdateUserProfileImageCommand;
import org.springframework.web.multipart.MultipartFile;

/**
 * Assembler to create an UpdateUserProfileImageCommand from a resource.
 */
public class UpdateUserProfileImageCommandFromResourceAssembler {

  public UpdateUserProfileImageCommandFromResourceAssembler() {
    // Prevent instantiation
  }

  /**
   * Transforms the parameters into an UpdateUserProfileImageCommand.
   *
   * @param userId the ID of the user
   * @param file   the multipart file to upload
   * @return the created command
   */
  public static UpdateUserProfileImageCommand toCommandFromResource(
      Long userId, MultipartFile file) {
    return new UpdateUserProfileImageCommand(userId, file);
  }
}
