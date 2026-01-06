package com.example.authentication.iam.application.internal.commandservices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleCommandServiceImplTest {

  @Mock
  private RoleRepository roleRepository;
  @InjectMocks
  private RoleCommandServiceImpl service;

  @Test
  @DisplayName("handle(SeedRolesCommand) seeds all missing roles (AAA)")
  void handle_ShouldSeed_AllMissingRoles() {
    // Arrange: simular que ningún role existe
    when(roleRepository.existsByRoles(any())).thenReturn(false);

    // Act
    service.handle(new SeedRolesCommand());

    // Assert: capturar todas las entidades guardadas
    ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
    verify(roleRepository, times(Roles.values().length)).save(captor.capture());
    List<Role> saved = captor.getAllValues();

    Set<Roles> savedRoles =
        saved.stream().map(Role::getRoles).collect(Collectors.toSet());

    Set<Roles> expected = new HashSet<>(Arrays.asList(Roles.values()));
    assertEquals(expected, savedRoles);

    // Comprobar también que se consultó existencia por cada role
    for (Roles r : Roles.values()) {
      verify(roleRepository).existsByRoles(r);
    }

    verifyNoMoreInteractions(roleRepository);
  }

  @Test
  @DisplayName("handle(SeedRolesCommand) skips existing roles (AAA)")
  void handle_ShouldSkip_WhenRoleExists() {
    // Arrange
    Roles[] vals = Roles.values();

    // Simular por defecto que ningún role existe, y marcar vals[0] como existente.
    when(roleRepository.existsByRoles(any())).thenReturn(false);
    when(roleRepository.existsByRoles(vals[0])).thenReturn(true);

    // Act
    service.handle(new SeedRolesCommand());

    // Assert
    // Capturar todas las entidades guardadas y comprobar que se guardaron
    // exactamente los roles que no existían (todos excepto vals[0]).
    ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
    verify(roleRepository, times(vals.length - 1)).save(captor.capture());
    List<Role> saved = captor.getAllValues();
    Set<Roles> savedRoles =
        saved.stream().map(Role::getRoles).collect(Collectors.toSet());

    Set<Roles> expected = new HashSet<>(Arrays.asList(Arrays.copyOfRange(vals, 1, vals.length)));
    assertEquals(expected, savedRoles);
    assertFalse(savedRoles.contains(vals[0]));

    verifyNoMoreInteractions(roleRepository);
  }
}
