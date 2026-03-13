package com.example.authentication.iam.interfaces.rest.transform;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.example.authentication.iam.domain.model.aggregates.User;

public class AuthenticatedUserResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResourceFromEntity - success: assembles AuthenticatedUserResource with correct fields")
  void toResourceFromEntity_success_assemblesAuthenticatedUserResourceWithCorrectFields() {

    // Arrange
    var user = mock(User.class);
    when(user.getId()).thenReturn(1L);
    when(user.getUsername()).thenReturn("alice");
    when(user.getEmail()).thenReturn("alice@example.com");
    var token = "jwt-token-123";

    // Act
    var result = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(user, token);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(1L);
    assertThat(result.username()).isEqualTo("alice");
    assertThat(result.email()).isEqualTo("alice@example.com");
    assertThat(result.token()).isEqualTo("jwt-token-123");
  }
}
