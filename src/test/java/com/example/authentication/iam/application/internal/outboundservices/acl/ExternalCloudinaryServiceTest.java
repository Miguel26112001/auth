package com.example.authentication.iam.application.internal.outboundservices.acl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.authentication.shared.domain.model.dto.CloudinaryResponse;
import com.example.authentication.shared.interfaces.acl.CloudinaryServiceContextFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ExternalCloudinaryServiceTest {

  @Mock
  CloudinaryServiceContextFacade cloudinaryServiceContextFacade;

  @InjectMocks
  ExternalCloudinaryService service;

  @Test
  @DisplayName("uploadImage - success: uploads image and returns CloudinaryResponse")
  void uploadImage_success_uploadsImageAndReturnsCloudinaryResponse() {

    // Arrange
    var file = new MockMultipartFile(
        "file",
        "profile.jpg",
        "image/jpeg",
        "image content".getBytes()
    );
    var expectedResponse = new CloudinaryResponse(
        "https://res.cloudinary.com/demo/image/upload/v1234567890/profile.jpg",
        "uploads/profile_123"
    );

    when(cloudinaryServiceContextFacade.uploadFile(file)).thenReturn(expectedResponse);

    // Act
    var result = service.uploadImage(file);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.url())
        .isEqualTo("https://res.cloudinary.com/demo/image/upload/v1234567890/profile.jpg");
    assertThat(result.publicId()).isEqualTo("uploads/profile_123");
    verify(cloudinaryServiceContextFacade).uploadFile(file);
  }

  @Test
  @DisplayName("uploadImage - null file: returns null")
  void uploadImage_whenFileIsNull_returnsNull() {

    // Act
    var result = service.uploadImage(null);

    // Assert
    assertThat(result).isNull();
  }

  @Test
  @DisplayName("uploadImage - empty file: returns null")
  void uploadImage_whenFileIsEmpty_returnsNull() {

    // Arrange
    var emptyFile = new MockMultipartFile(
        "file",
        "empty.jpg",
        "image/jpeg",
        new byte[0]
    );

    // Act
    var result = service.uploadImage(emptyFile);

    // Assert
    assertThat(result).isNull();
  }

  @Test
  @DisplayName("uploadImage - ACL throws exception: catches and returns null")
  void uploadImage_whenAclThrowsException_catchesAndReturnsNull() {

    // Arrange
    var file = new MockMultipartFile(
        "file",
        "image.jpg",
        "image/jpeg",
        "image content".getBytes()
    );

    when(cloudinaryServiceContextFacade.uploadFile(any()))
        .thenThrow(new RuntimeException("Cloudinary service error"));

    // Act
    var result = service.uploadImage(file);

    // Assert
    assertThat(result).isNull();
    verify(cloudinaryServiceContextFacade).uploadFile(file);
  }

  @Test
  @DisplayName("deleteImage - success: delegates deletion to facade")
  void deleteImage_success_delegatesDeletionToFacade() {

    // Arrange
    var publicId = "uploads/profile_123";

    // Act
    service.deleteImage(publicId);

    // Assert
    verify(cloudinaryServiceContextFacade).deleteFile(publicId);
  }

  @Test
  @DisplayName("deleteImage - ACL throws exception: catches exception silently")
  void deleteImage_whenAclThrowsException_catchesExceptionSilently() {

    // Arrange
    var publicId = "uploads/profile_123";

    doThrow(new RuntimeException("Cloudinary delete error"))
        .when(cloudinaryServiceContextFacade)
        .deleteFile(publicId);

    // Act & Assert (should not throw)
    service.deleteImage(publicId);

    verify(cloudinaryServiceContextFacade).deleteFile(publicId);
  }
}

