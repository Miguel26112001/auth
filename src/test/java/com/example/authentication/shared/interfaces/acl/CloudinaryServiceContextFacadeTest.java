package com.example.authentication.shared.interfaces.acl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.authentication.shared.domain.model.dto.CloudinaryResponse;
import com.example.authentication.shared.domain.services.CloudinaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceContextFacadeTest {

  @Mock
  CloudinaryService cloudinaryService;

  @InjectMocks
  CloudinaryServiceContextFacade facade;

  @Test
  @DisplayName("uploadFile - success: delegates upload to internal Cloudinary service")
  void uploadFile_success_delegatesUploadToInternalCloudinaryService() {

    // Arrange
    var file = new MockMultipartFile(
        "file",
        "image.jpg",
        "image/jpeg",
        "file content".getBytes()
    );
    var expectedResponse = new CloudinaryResponse(
        "https://res.cloudinary.com/demo/image/upload/v1234567890/image.jpg",
        "public_id_123"
    );

    when(cloudinaryService.upload(any())).thenReturn(expectedResponse);

    // Act
    var result = facade.uploadFile(file);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.url())
        .isEqualTo("https://res.cloudinary.com/demo/image/upload/v1234567890/image.jpg");
    assertThat(result.publicId()).isEqualTo("public_id_123");
    verify(cloudinaryService).upload(file);
  }

  @Test
  @DisplayName("deleteFile - success: delegates delete to internal Cloudinary service")
  void deleteFile_success_delegatesDeleteToInternalCloudinaryService() {

    // Arrange
    var publicId = "public_id_123";

    // Act
    facade.deleteFile(publicId);

    // Assert
    verify(cloudinaryService).delete(publicId);
  }
}

