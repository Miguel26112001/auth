package com.example.authentication.shared.application.internal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceImplTest {

  @Mock
  Cloudinary cloudinary;

  CloudinaryServiceImpl service;

  @Test
  @DisplayName("upload - success: uploads file and returns CloudinaryResponse")
  void upload_success_uploadsFileAndReturnsCloudinaryResponse() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var file = new MockMultipartFile(
        "file",
        "image.jpg",
        "image/jpeg",
        "image content".getBytes()
    );

    Map<String, Object> uploadResult = new HashMap<>();
    uploadResult.put("secure_url", "https://res.cloudinary.com/demo/image/upload/v1234567890/uploads/image.jpg");
    uploadResult.put("public_id", "uploads/image_123");

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.upload(any(byte[].class), anyMap())).thenReturn(uploadResult);

    // Act
    var result = service.upload(file);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.url())
        .isEqualTo("https://res.cloudinary.com/demo/image/upload/v1234567890/uploads/image.jpg");
    assertThat(result.publicId()).isEqualTo("uploads/image_123");
    verify(cloudinary).uploader();
    verify(uploaderMock).upload(any(byte[].class), anyMap());
  }

  @Test
  @DisplayName("upload - error: throws RuntimeException when upload fails")
  void upload_whenUploadFails_throwsRuntimeException() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var file = new MockMultipartFile(
        "file",
        "image.jpg",
        "image/jpeg",
        "image content".getBytes()
    );

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.upload(any(byte[].class), anyMap()))
        .thenThrow(new RuntimeException("Cloudinary API error"));

    // Act & Assert
    var exception = assertThrows(RuntimeException.class, () -> service.upload(file));

    assertThat(exception.getMessage()).contains("Error uploading file to Cloudinary");
    assertThat(exception.getCause()).isInstanceOf(Exception.class);
  }

  @Test
  @DisplayName("upload - null file: throws exception when getting bytes")
  void upload_whenFileIsNull_throwsRuntimeException() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var file = new MockMultipartFile(
        "file",
        "image.jpg",
        "image/jpeg",
        "content".getBytes()
    );

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.upload(any(byte[].class), anyMap()))
        .thenThrow(new NullPointerException("File is null"));

    // Act & Assert
    var exception = assertThrows(RuntimeException.class, () -> service.upload(file));

    assertThat(exception.getMessage()).contains("Error uploading file to Cloudinary");
  }

  @Test
  @DisplayName("delete - success: deletes file from Cloudinary")
  void delete_success_deletesFileFromCloudinary() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var publicId = "uploads/image_123";

    Map<String, Object> deleteResult = new HashMap<>();
    deleteResult.put("result", "ok");

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.destroy(eq(publicId), anyMap())).thenReturn(deleteResult);

    // Act
    service.delete(publicId);

    // Assert
    verify(cloudinary).uploader();
    verify(uploaderMock).destroy(eq(publicId), anyMap());
  }

  @Test
  @DisplayName("delete - error: throws RuntimeException when delete fails")
  void delete_whenDeleteFails_throwsRuntimeException() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var publicId = "uploads/image_123";

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.destroy(any(), anyMap()))
        .thenThrow(new RuntimeException("Cloudinary delete error"));

    // Act & Assert
    var exception = assertThrows(RuntimeException.class, () -> service.delete(publicId));

    assertThat(exception.getMessage()).contains("Error deleting file from Cloudinary");
    assertThat(exception.getCause()).isInstanceOf(Exception.class);
  }

  @Test
  @DisplayName("delete - invalid public ID: throws RuntimeException")
  void delete_whenPublicIdIsInvalid_throwsRuntimeException() throws Exception {

    // Arrange
    service = new CloudinaryServiceImpl(cloudinary);
    var publicId = "invalid_id";

    var uploaderMock = mock(Uploader.class);
    when(cloudinary.uploader()).thenReturn(uploaderMock);
    when(uploaderMock.destroy(any(), anyMap()))
        .thenThrow(new RuntimeException("Invalid public ID"));

    // Act & Assert
    var exception = assertThrows(RuntimeException.class, () -> service.delete(publicId));

    assertThat(exception.getMessage()).contains("Error deleting file from Cloudinary");
  }
}
