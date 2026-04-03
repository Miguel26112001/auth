package com.example.authentication.iam.interfaces.rest.transform;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class UpdateUserProfileImageCommandFromResourceAssemblerTest {

  @Test
  @DisplayName("toCommandFromResource - success: assembles UpdateUserProfileImageCommand with correct fields")
  void toCommandFromResource_success_assemblesUpdateUserProfileImageCommandWithCorrectFields() {

    // Arrange
    Long userId = 1L;
    var file = new MockMultipartFile(
        "file",
        "profile.jpg",
        "image/jpeg",
        "file content".getBytes()
    );

    // Act
    var result = UpdateUserProfileImageCommandFromResourceAssembler
        .toCommandFromResource(userId, file);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.userId()).isEqualTo(1L);
    assertThat(result.file()).isNotNull();
    assertThat(result.file().getOriginalFilename()).isEqualTo("profile.jpg");
    assertThat(result.file().getContentType()).isEqualTo("image/jpeg");
  }
}
