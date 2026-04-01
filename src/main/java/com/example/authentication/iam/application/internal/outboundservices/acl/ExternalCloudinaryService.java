package com.example.authentication.iam.application.internal.outboundservices.acl;

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
  public String uploadImage(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return "";
    }

    try {
      return cloudinaryServiceContextFacade.uploadFile(file);
    } catch (Exception e) {
      // Log the error while maintaining the resilience of the IAM service
      System.err.println("Error al subir imagen a través de la fachada ACL: " + e.getMessage());
      return "";
    }
  }
}
