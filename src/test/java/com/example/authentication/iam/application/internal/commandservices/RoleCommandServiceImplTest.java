package com.example.authentication.iam.application.internal.commandservices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    org.junit.jupiter.api.Assertions.assertEquals(expected, savedRoles);

    // Comprobar también que se consultó existencia por cada role
    for (Roles r : Roles.values()) {
      verify(roleRepository).existsByRoles(r);
    }

    verifyNoMoreInteractions(roleRepository);
  }
}
