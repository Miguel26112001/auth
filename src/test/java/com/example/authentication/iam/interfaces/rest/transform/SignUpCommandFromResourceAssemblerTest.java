package com.example.authentication.iam.interfaces.rest.transform;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.authentication.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource - success: assembles SignUpCommand with valid roles")
  void toCommandFromResource_success_assemblesSignUpCommandWithValidRoles() {

    // Arrange
    var resource = new SignUpResource(
        "alice",
        "alice@example.com",
        "password123",
        List.of("ROLE_USER")
    );

    var baseUrl = "http://localhost:8080";

    // Act
    var result = SignUpCommandFromResourceAssembler
        .toCommandFromResource(resource, baseUrl);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.username()).isEqualTo("alice");
    assertThat(result.email()).isEqualTo("alice@example.com");
    assertThat(result.password()).isEqualTo("password123");
    assertThat(result.roles().size()).isEqualTo(1);
    assertThat(result.baseUrl()).isEqualTo(baseUrl);
  }

  @Test
  @DisplayName("toCommandFromResource - edge case: handles null roles")
  void toCommandFromResource_whenRolesAreNull_returnsCommandWithValidatedRoles() {

    // Arrange
    var resource = new SignUpResource(
        "carol",
        "carol@example.com",
        "password123",
        null
    );

    // Act
    var result = SignUpCommandFromResourceAssembler
        .toCommandFromResource(resource, "http://localhost");

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.roles()).isNotNull();
  }
}
