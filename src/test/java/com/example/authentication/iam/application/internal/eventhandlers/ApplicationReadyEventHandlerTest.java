package com.example.authentication.iam.application.internal.eventhandlers;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;
import com.example.authentication.iam.domain.services.RoleCommandService;

@ExtendWith(MockitoExtension.class)
class ApplicationReadyEventHandlerTest {

  @Mock
  RoleCommandService roleCommandService;

  @InjectMocks
  ApplicationReadyEventHandler handler;

  @Test
  @DisplayName("on(ApplicationReadyEvent) - success: calls roleCommandService.handle with SeedRolesCommand")
  void on_applicationReadyEvent_success_callsRoleCommandServiceHandleWithSeedRolesCommand() {
    // Arrange
    var applicationContext = mock(ConfigurableApplicationContext.class);
    when(applicationContext.getId()).thenReturn("test-app");

    var event = mock(ApplicationReadyEvent.class);
    when(event.getApplicationContext()).thenReturn(applicationContext);

    // Act
    handler.on(event);

    // Assert
    verify(roleCommandService).handle(new SeedRolesCommand());
  }

  @Test
  @DisplayName("on(ApplicationReadyEvent) - service throws exception: propagates exception")
  void on_applicationReadyEvent_serviceThrowsException_propagatesException() {
    // Arrange
    var applicationContext = mock(ConfigurableApplicationContext.class);
    when(applicationContext.getId()).thenReturn("test-app");

    var event = mock(ApplicationReadyEvent.class);
    when(event.getApplicationContext()).thenReturn(applicationContext);

    doThrow(new RuntimeException("Seeding failed")).when(roleCommandService).handle(any(SeedRolesCommand.class));

    // Act & Assert
    org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> handler.on(event));
  }
}
