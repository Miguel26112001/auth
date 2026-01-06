package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.model.commands.VerifyUserCommand;
import com.example.authentication.iam.domain.services.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST controller responsible for authentication-related operations.
 *
 * <p>Provides endpoints for user verification and authentication workflows.</p>
 */
@Controller
@RequestMapping(value = "/api/v1/authentication")
@Tag(
    name = "Authentication",
    description = "Authentication endpoints for user and token management"
)
public class AuthenticationController {

  private final UserCommandService userCommandService;

  /**
   * Creates a new {@code AuthenticationController}.
   *
   * @param userCommandService service responsible for handling user commands
   */
  public AuthenticationController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  /**
   * Verifies a user using a verification token.
   *
   * @param token verification token provided to the user
   * @param model Spring MVC model used to pass attributes to the view
   * @return the name of the HTML view to render
   */
  @GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
  @Operation(
      summary = "Verify a user",
      description = "Allows verifying a user in the system by providing a verification token."
  )
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "User verified successfully",
              content = @Content(mediaType = "text/html")
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Invalid token",
              content = @Content(mediaType = "text/html")
          )
      }
  )
  public String verify(@RequestParam String token, Model model) {
    try {
      VerifyUserCommand verifyUserCommand = new VerifyUserCommand(token);
      userCommandService.handle(verifyUserCommand);
      return "verification-success";
    } catch (Exception e) {
      model.addAttribute("error", e.getMessage());
      return "verification-error";
    }
  }
}
