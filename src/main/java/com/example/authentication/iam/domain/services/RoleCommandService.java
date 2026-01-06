package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;

/**
 * Command service responsible for role-related write operations.
 */
public interface RoleCommandService {

  /**
   * Handles the command to seed default roles in the system.
   *
   * @param command command used to initialize system roles
   */
  void handle(SeedRolesCommand command);
}
