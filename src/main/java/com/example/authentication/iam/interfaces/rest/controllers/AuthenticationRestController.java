package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.example.authentication.iam.interfaces.rest.resources.SignInResource;
import com.example.authentication.iam.interfaces.rest.resources.SignUpResource;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import com.example.authentication.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.example.authentication.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller responsible for authentication operations.
 *
 * <p>Provides endpoints for user registration and authentication.</p>
 */
@RestController
@RequestMapping(
    value = "/api/v1/authentication",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(
    name = "Authentication",
    description = "Authentication endpoints for user and token management"
)
public class AuthenticationRestController {

  private final UserCommandService userCommandService;

  /**
   * Creates a new {@code AuthenticationRestController}.
   *
   * @param userCommandService service responsible for user commands
   */
  public AuthenticationRestController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  /**
   * Authenticates a user using their credentials.
   *
   * @param signInResource resource containing username and password
   * @return authenticated user with access token
   */
  @PostMapping("/sign-in")
  @Operation(
      summary = "Log in into the application",
      description = "Authenticates a user using username and password. "
          + "If valid, an access token is returned."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User authenticated successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = AuthenticatedUserResource.class
                  )
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Invalid password",
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
      }
  )
  public ResponseEntity<?> signIn(
      @RequestBody SignInResource signInResource) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);

    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.status(400).body(new MessageResource("Something went wrong."));
    }

    var authenticatedUserResource =
        AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
            authenticatedUser.get().getLeft(),
            authenticatedUser.get().getRight()
        );

    return ResponseEntity.ok(authenticatedUserResource);
  }

  /**
   * Registers a new user in the system.
   *
   * @param signUpResource resource containing user registration data
   * @return created user resource
   */
  @PostMapping("/sign-up")
  @Operation(
      summary = "Register a new user",
      description = "Registers a new user with username, password, and roles."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201",
              description = "User created successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserResource.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Bad request",
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
          ),
          @ApiResponse(
              responseCode = "409",
              description = "Username already exists",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = MessageResource.class)
              )
          )
      }
  )
  public ResponseEntity<UserResource> signUp(
      @Valid @RequestBody SignUpResource signUpResource) {

    String baseUrl = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .build()
        .toUriString();

    var signUpCommand = SignUpCommandFromResourceAssembler
        .toCommandFromResource(signUpResource, baseUrl);

    var user = userCommandService.handle(signUpCommand);
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    var userResource =
        UserResourceFromEntityAssembler.toResourceFromEntity(user.get());

    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }
}
