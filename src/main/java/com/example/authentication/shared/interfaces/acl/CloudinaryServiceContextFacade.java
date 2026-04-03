package com.example.authentication.shared.interfaces.acl;

import com.example.authentication.shared.domain.model.dto.CloudinaryResponse;
import com.example.authentication.shared.domain.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Cloudinary service context facade.
 * <p>
 * This facade serves as an anti-corruption layer (ACL) to expose
 * Cloudinary-related functionality to other contexts.
 * </p>
 */
@Service
public class CloudinaryServiceContextFacade {

  private final CloudinaryService cloudinaryService;

  /**
   * Constructs a {@code CloudinaryServiceContextFacade}.
   *
   * @param cloudinaryService the internal Cloudinary service
   */
  public CloudinaryServiceContextFacade(CloudinaryService cloudinaryService) {
    this.cloudinaryService = cloudinaryService;
  }

  /**
   * Uploads a file using the internal Cloudinary service.
   *
   * @param file the multipart file to upload
   * @return the secure URL of the uploaded file
   */
  public CloudinaryResponse uploadFile(MultipartFile file) {
    return cloudinaryService.upload(file);
  }

  /**
   * Deletes a file from Cloudinary using the internal service.
   *
   * @param publicId the public ID of the file to delete
   */
  public void deleteFile(String publicId) {
    cloudinaryService.delete(publicId);
  }
}
