package com.example.authentication.iam.interfaces.rest.transform;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.example.authentication.iam.domain.model.entities.Role;

class RoleResourceFromEntityAssemblerTest {

  @Test
  @DisplayName("toResourceFromEntity - success: assembles RoleResource with correct fields")
  void toResourceFromEntity_success_assemblesRoleResourceWithCorrectFields() {

    // Arrange
    var role = mock(Role.class);
    when(role.getId()).thenReturn(1L);
    when(role.getStringRole()).thenReturn("ROLE_ADMIN");

    // Act
    var result = RoleResourceFromEntityAssembler.toResourceFromEntity(role);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(1L);
    assertThat(result.name()).isEqualTo("ROLE_ADMIN");
  }
}
