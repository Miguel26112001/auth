package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.model.commands.UpdateUserStatusCommand;
import com.example.authentication.iam.domain.model.queries.GetAllUsersQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByIdQuery;
import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.domain.services.UserQueryService;
import com.example.authentication.iam.interfaces.rest.resources.UpdatePasswordResource;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import com.example.authentication.iam.interfaces.rest.transform.UpdatePasswordCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing users.
 *
 * <p>Allows retrieving, updating status, and updating password of users.
 * Some operations are restricted to administrators.</p>
 */
@RestController
@RequestMapping(
    value = "/api/v1/users",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(
    name = "Users",
    description = "User management endpoints"
)
public class UsersController {

  private final UserQueryService userQueryService;
  private final UserCommandService userCommandService;

  /**
   * Creates a new {@code UsersController}.
   *
   * @param userQueryService   service used to query users
   * @param userCommandService service used to execute user commands
   */
  public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
    this.userQueryService = userQueryService;
    this.userCommandService = userCommandService;
  }

  /**
   * Retrieves all users.
   *
   * @return list of user resources
   */
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Get all users", description = "Retrieve all registered users.")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Users found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      )
  })
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var users = userQueryService.handle(new GetAllUsersQuery());
    var userResources = users.stream()
        .map(UserResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(userResources);
  }

  /**
   * Retrieves a user by ID.
   *
   * @param userId the ID of the user
   * @return user resource or 404 if not found
   */
  @GetMapping("/{userId}")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
  @Operation(summary = "Get user by ID", description = "Retrieve a specific user by ID.")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      )
  })
  public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    var user = userQueryService.handle(new GetUserByIdQuery(userId));
    if (user.isEmpty()) {
      return ResponseEntity.status(404)
          .body(new MessageResource("User not found with ID: " + userId));
    }
    return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user.get()));
  }

  /**
   * Updates the status of a user.
   *
   * @param userId  the ID of the user
   * @param isActive new status of the user
   * @return updated user resource
   */
  @PatchMapping("/{userId}/status")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id)")
  @Operation(summary = "Update user status",
      description = "Update the active status of a specific user.")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User status updated",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      )
  })
  public ResponseEntity<UserResource> updateUserStatus(
      @PathVariable Long userId,
      @RequestParam boolean isActive) {

    var updatedUser = userCommandService.handle(new UpdateUserStatusCommand(userId, isActive));
    if (updatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity
        .ok(UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get()));
  }

  /**
   * Updates the password of a user.
   *
   * @param userId  the ID of the user
   * @param resource new password resource
   * @return updated user resource
   */
  @PatchMapping("/{userId}/password")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id)")
  @Operation(summary = "Update user password",
      description = "Update the password of a specific user.")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "User password updated",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Weak password or invalid request",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      ),
      @ApiResponse(
          responseCode = "404",
          description = "User not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MessageResource.class)
          )
      )
  })
  public ResponseEntity<UserResource> updateUserPassword(
      @PathVariable Long userId,
      @RequestBody UpdatePasswordResource resource) {

    var updatedUser = userCommandService.handle(
        UpdatePasswordCommandFromResourceAssembler.toCommandFromResource(userId, resource)
    );

    if (updatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity
        .ok(UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get()));
  }
}