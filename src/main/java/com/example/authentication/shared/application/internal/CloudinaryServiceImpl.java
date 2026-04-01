package com.example.authentication.shared.application.internal;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.authentication.shared.domain.services.CloudinaryService;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
  private final Cloudinary cloudinary;

  public CloudinaryServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public String upload(MultipartFile file) {
    try {
      Map result = cloudinary.uploader().upload(
          file.getBytes(),
          ObjectUtils.asMap(
              "folder", "uploads"
          )
      );

      return result.get("secure_url").toString();

    } catch (Exception e) {
      throw new RuntimeException("Error subiendo archivo", e);
    }
  }
}
