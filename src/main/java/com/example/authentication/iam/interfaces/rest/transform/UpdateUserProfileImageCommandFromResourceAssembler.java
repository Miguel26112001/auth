package com.example.authentication.iam.interfaces.rest.transform;

import org.springframework.web.multipart.MultipartFile;
import com.example.authentication.iam.domain.model.commands.UpdateUserProfileImageCommand;

public class UpdateUserProfileImageCommandFromResourceAssembler {
  public static UpdateUserProfileImageCommand toCommandFromResource(Long userId, MultipartFile file) {
    return new UpdateUserProfileImageCommand(userId, file);
  }
}
