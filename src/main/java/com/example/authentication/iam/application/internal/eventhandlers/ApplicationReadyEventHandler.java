package com.example.authentication.iam.application.internal.eventhandlers;

import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;
import com.example.authentication.iam.domain.services.RoleCommandService;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Event handler that listens for the application-ready event
 * and triggers the role seeding process if required.
 */
@Service
public class ApplicationReadyEventHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

  private final RoleCommandService roleCommandService;

  /**
   * Creates a new {@code ApplicationReadyEventHandler}.
   *
   * @param roleCommandService service responsible for role-related commands
   */
  public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
    this.roleCommandService = roleCommandService;
  }

  /**
   * Handles the {@link ApplicationReadyEvent}.
   *
   * @param event application ready event
   */
  @EventListener
  public void on(ApplicationReadyEvent event) {
    var applicationName = event.getApplicationContext().getId();

    LOGGER.info(
        "Starting to verify if roles seeding is needed for {} at {}",
        applicationName,
        currentTimestamp());

    var seedRolesCommand = new SeedRolesCommand();
    roleCommandService.handle(seedRolesCommand);

    LOGGER.info(
        "Roles seeding verification finished for {} at {}",
        applicationName,
        currentTimestamp());
  }

  /**
   * Returns the current timestamp.
   *
   * @return current timestamp
   */
  private Timestamp currentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }
}
