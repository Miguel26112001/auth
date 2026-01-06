package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;
import com.example.authentication.iam.domain.services.RoleQueryService;
import com.example.authentication.iam.interfaces.rest.resources.RoleResource;
import com.example.authentication.iam.interfaces.rest.transform.RoleResourceFromEntityAssembler;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for role management operations.
 *
 * <p>Allows querying all roles or retrieving a role by its name.
 * Access is restricted to administrators.</p>
 */
@RestController
@RequestMapping(
    value = "/api/v1/roles",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(
    name = "Roles",
    description = "Role management endpoints"
)
@PreAuthorize("hasRole('ADMIN')")
public class RolesController {

  private final RoleQueryService roleQueryService;

  /**
   * Creates a new {@code RolesController}.
   *
   * @param roleQueryService service used to query roles
   */
  public RolesController(RoleQueryService roleQueryService) {
    this.roleQueryService = roleQueryService;
  }

  /**
   * Retrieves roles from the system.
   *
   * <p>If a name is provided, returns the role with that name.
   * Otherwise, returns all available roles.</p>
   *
   * @param name optional role name to filter by
   * @return list of roles or a single role resource
   */
  @GetMapping
  @Operation(
      summary = "Get roles",
      description = "Returns all roles or a specific role if a name is provided."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Roles found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RoleResource.class)
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
              description = "Role not found",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = MessageResource.class)
              )
          )
      }
  )
  public ResponseEntity<?> getRoles(
      @RequestParam(name = "name", required = false) String name) {

    if (name == null || name.isBlank()) {
      var getAllRolesQuery = new GetAllRolesQuery();
      var roles = roleQueryService.handle(getAllRolesQuery);

      var roleResources = roles.stream()
          .map(RoleResourceFromEntityAssembler::toResourceFromEntity)
          .toList();

      return ResponseEntity.ok(roleResources);
    }

    var getRoleByNameQuery = new GetRoleByNameQuery(name);
    var role = roleQueryService.handle(getRoleByNameQuery);

    if (role.isEmpty()) {
      return ResponseEntity.status(404)
          .body(new MessageResource(
              "Role not found with name: " + name
          ));
    }

    var roleResource =
        RoleResourceFromEntityAssembler.toResourceFromEntity(role.get());

    return ResponseEntity.ok(roleResource);
  }
}
