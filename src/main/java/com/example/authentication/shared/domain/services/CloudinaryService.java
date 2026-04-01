package com.example.authentication.shared.domain.services;

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
  String upload(MultipartFile file);
}