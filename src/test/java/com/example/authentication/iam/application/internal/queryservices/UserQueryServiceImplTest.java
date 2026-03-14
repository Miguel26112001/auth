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
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.queries.GetAllUsersQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByIdQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByUsernameQuery;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserQueryServiceImpl service;

  @Test
  @DisplayName("handle(GetAllUsersQuery) - success: returns all users from repository")
  void handle_getAllUsers_success_returnsAllUsersFromRepository() {
    // Arrange
    var users = List.of(mock(User.class), mock(User.class));
    when(userRepository.findAll()).thenReturn(users);

    var query = new GetAllUsersQuery();

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEqualTo(users);
  }

  @Test
  @DisplayName("handle(GetUserByIdQuery) - user found: returns user")
  void handle_getUserById_userFound_returnsUser() {
    // Arrange
    var userId = 1L;
    var user = mock(User.class);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    var query = new GetUserByIdQuery(userId);

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).containsSame(user);
  }

  @Test
  @DisplayName("handle(GetUserByIdQuery) - user not found: returns empty")
  void handle_getUserById_userNotFound_returnsEmpty() {
    // Arrange
    var userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    var query = new GetUserByIdQuery(userId);

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("handle(GetUserByUsernameQuery) - user found: returns user")
  void handle_getUserByUsername_userFound_returnsUser() {
    // Arrange
    var username = "alice";
    var user = mock(User.class);
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

    var query = new GetUserByUsernameQuery(username);

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).containsSame(user);
  }

  @Test
  @DisplayName("handle(GetUserByUsernameQuery) - user not found: returns empty")
  void handle_getUserByUsername_userNotFound_returnsEmpty() {
    // Arrange
    var username = "missing";
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    var query = new GetUserByUsernameQuery(username);

    // Act
    var result = service.handle(query);

    // Assert
    assertThat(result).isEmpty();
  }
}
