package com.example.authentication.iam.interfaces.rest.transform;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.authentication.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource - success: assembles SignInCommand with correct fields")
  void toCommandFromResource_success_assemblesSignInCommandWithCorrectFields() {

    // Arrange
    var resource = new SignInResource("alice", "password123");

    // Act
    var result = SignInCommandFromResourceAssembler.toCommandFromResource(resource);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.username()).isEqualTo("alice");
    assertThat(result.password()).isEqualTo("password123");
  }
}
