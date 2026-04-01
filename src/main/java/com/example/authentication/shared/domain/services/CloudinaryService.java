package com.example.authentication.shared.domain.services;

import com.example.authentication.shared.domain.model.dto.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for Cloudinary media management.
 * <p>
 * Defines the contract for uploading files to the Cloudinary cloud storage.
 * </p>
 */
public interface CloudinaryService {

  /**
   * Uploads a file to the cloud storage.
   *
   * @param file the multipart file to upload
   * @return the secure URL of the uploaded file
   */
  CloudinaryResponse upload(MultipartFile file);

  /**
   * Deletes a file from Cloudinary using its public ID.
   *
   * @param publicId the public ID of the image to delete
   */
  void delete(String publicId);
}