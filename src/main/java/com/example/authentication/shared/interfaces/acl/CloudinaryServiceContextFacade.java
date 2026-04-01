package com.example.authentication.shared.interfaces.acl;

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
  public String uploadFile(MultipartFile file) {
    return cloudinaryService.upload(file);
  }
}
