package com.example.authentication.iam.interfaces.rest.transform;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.authentication.iam.interfaces.rest.resources.UpdatePasswordResource;

class UpdatePasswordCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource - success: assembles UpdatePasswordCommand with correct fields")
  void toCommandFromResource_success_assemblesUpdatePasswordCommandWithCorrectFields() {

    // Arrange
    Long userId = 1L;
    var resource = new UpdatePasswordResource("oldPassword", "newPassword");

    // Act
    var result = UpdatePasswordCommandFromResourceAssembler
        .toCommandFromResource(userId, resource);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.userId()).isEqualTo(1L);
    assertThat(result.currentPassword()).isEqualTo("oldPassword");
    assertThat(result.newPassword()).isEqualTo("newPassword");
  }
}
