package com.example.authentication.iam.application.internal.queryservices;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleQueryServiceImplTest {

  @Mock
  RoleRepository roleRepository;

  @InjectMocks
  RoleQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllRolesQuery) - success: returns all roles from repository")
  void handle_getAllRoles_success_returnsAllRolesFromRepository() {
    // Arrange
    var roles = List.of(mock(Role.class), mock(Role.class));
    when(roleRepository.findAll()).thenReturn(roles);

    var query = new GetAllRolesQuery();

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEqualTo(roles);
  }

  @Test
  @DisplayName("handle(GetRoleByNameQuery) - valid role with ROLE_ prefix: returns role")
  void handle_getRoleByName_validRoleWithPrefix_returnsRole() {
    // Arrange
    var role = mock(Role.class);
    var query = new GetRoleByNameQuery("ROLE_USER");

    when(roleRepository.findByRoles(Roles.ROLE_USER)).thenReturn(Optional.of(role));

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(role);
  }

  @Test
  @DisplayName("handle(GetRoleByNameQuery) - valid role without ROLE_ prefix: adds prefix and returns role")
  void handle_getRoleByName_validRoleWithoutPrefix_addsPrefixAndReturnsRole() {
    // Arrange
    var role = mock(Role.class);
    var query = new GetRoleByNameQuery("USER");

    when(roleRepository.findByRoles(Roles.ROLE_USER)).thenReturn(Optional.of(role));

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get()).isSameAs(role);
  }

  @Test
  @DisplayName("handle(GetRoleByNameQuery) - invalid role name: returns empty")
  void handle_getRoleByName_invalidRoleName_returnsEmpty() {
    // Arrange
    var query = new GetRoleByNameQuery("INVALID_ROLE");

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("handle(GetRoleByNameQuery) - valid role not found in repository: returns empty")
  void handle_getRoleByName_validRoleNotFound_returnsEmpty() {
    // Arrange
    var query = new GetRoleByNameQuery("ROLE_ADMIN");

    when(roleRepository.findByRoles(Roles.ROLE_ADMIN)).thenReturn(Optional.empty());

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEmpty();
  }
}
