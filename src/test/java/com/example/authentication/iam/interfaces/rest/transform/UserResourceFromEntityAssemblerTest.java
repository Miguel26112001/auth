package com.example.authentication.iam.interfaces.rest.transform;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.entities.Role;

class UserResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResourceFromEntity - success: assembles UserResource with correct fields")
  void toResourceFromEntity_success_assemblesUserResourceWithCorrectFields() {

    // Arrange
    User user = new User("alice", "alice@example.com", "hashedPassword");

    Role role = mock(Role.class);
    when(role.getStringRole()).thenReturn("ROLE_USER");

    Set<Role> roles = new HashSet<>();
    roles.add(role);
    user.setRoles(roles);

    // Act
    var result = UserResourceFromEntityAssembler.toResourceFromEntity(user);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.username()).isEqualTo("alice");
    assertThat(result.email()).isEqualTo("alice@example.com");
    assertThat(result.roles()).containsExactly("ROLE_USER");
  }
}
