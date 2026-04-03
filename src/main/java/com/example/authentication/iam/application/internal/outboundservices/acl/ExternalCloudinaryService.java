package com.example.authentication.iam.application.internal.outboundservices.acl;

import com.example.authentication.shared.domain.model.dto.CloudinaryResponse;
import com.example.authentication.shared.interfaces.acl.CloudinaryServiceContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * External Cloudinary service.
 * <p>
 * This service acts as an outbound service for the IAM context to interact
 * with the Cloudinary shared context through its ACL facade.
 * </p>
 */
@Service
public class ExternalCloudinaryService {

  private final CloudinaryServiceContextFacade cloudinaryServiceContextFacade;

  /**
   * Constructs an {@code ExternalCloudinaryService}.
   *
   * @param cloudinaryServiceContextFacade the Cloudinary service context facade
   */
  public ExternalCloudinaryService(CloudinaryServiceContextFacade cloudinaryServiceContextFacade) {
    this.cloudinaryServiceContextFacade = cloudinaryServiceContextFacade;
  }

  /**
   * Uploads an image file to Cloudinary.
   *
   * @param file the multipart file to upload
   * @return the secure URL of the uploaded image or an empty string if the upload fails
   */
  public CloudinaryResponse uploadImage(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return null;
    }

    try {
      return cloudinaryServiceContextFacade.uploadFile(file);
    } catch (Exception e) {
      System.err.println("Error uploading image through ACL: " + e.getMessage());
      return null;
    }
  }

  /**
   * Deletes an image from Cloudinary using its public ID.
   *
   * @param publicId the public ID of the image to delete
   */
  public void deleteImage(String publicId) {
    try {
      cloudinaryServiceContextFacade.deleteFile(publicId);
    } catch (Exception e) {
      System.err.println("Could not delete image: " + e.getMessage());
    }
  }
}
