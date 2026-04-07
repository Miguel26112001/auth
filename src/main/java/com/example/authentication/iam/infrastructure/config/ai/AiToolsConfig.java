package com.example.authentication.iam.infrastructure.config.ai;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import com.example.authentication.iam.domain.exceptions.UserNotFoundException;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;

@Component
public class AiToolsConfig {

  private final UserRepository userRepository;

  public AiToolsConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Tool(
      name = "getUserByUsername",
      description = "Retrieve a user by their username"
  )
  public UserResource getUserByUsername(String username) {
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return new UserResource(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getRoles().stream().map(Role::getStringRole).toList(),
        user.isActive()
    );
  }
}
