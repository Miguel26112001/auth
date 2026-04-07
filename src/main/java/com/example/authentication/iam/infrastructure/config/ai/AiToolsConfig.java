package com.example.authentication.iam.infrastructure.config.ai;

import com.example.authentication.iam.domain.exceptions.UserNotFoundException;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

/**
 * Configuration class for AI tools.
 * Provides tool methods that can be invoked by the AI assistant.
 */
@Component
public class AiToolsConfig {

  private final UserRepository userRepository;

  public AiToolsConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves a user by their username.
   *
   * @param username the username of the user to retrieve
   * @return a {@link UserResource} containing the user's details
   * @throws UserNotFoundException if no user is found with the given username
   */
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
