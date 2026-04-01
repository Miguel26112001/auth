package com.example.authentication.shared.application.internal;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.authentication.shared.domain.services.CloudinaryService;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of the Cloudinary service for file uploads.
 * <p>
 * This service provides the logic to upload multipart files to the
 * Cloudinary cloud storage and retrieve their secure URLs.
 * </p>
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  /**
   * Constructs a {@code CloudinaryServiceImpl}.
   *
   * @param cloudinary the Cloudinary instance to be used for uploads
   */
  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  /**
   * Uploads a file to Cloudinary.
   *
   * @param file the multipart file to upload
   * @return the secure URL of the uploaded file
   * @throws RuntimeException if an error occurs during the upload process
   */
  @Override
  public String upload(MultipartFile file) {
    try {
      Map<?, ?> result = cloudinary.uploader().upload(
          file.getBytes(),
          ObjectUtils.asMap(
              "folder", "uploads"
          )
      );

      return result.get("secure_url").toString();

    } catch (Exception e) {
      throw new RuntimeException("Error uploading file to Cloudinary", e);
    }
  }
}
