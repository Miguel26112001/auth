package com.example.authentication.shared.interfaces.acl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.authentication.shared.domain.services.CloudinaryService;

@Service
public class CloudinaryServiceContextFacade {
  private final CloudinaryService cloudinaryService;

  public CloudinaryServiceContextFacade(CloudinaryService cloudinaryService) {
    this.cloudinaryService = cloudinaryService;
  }

  public String uploadFile(MultipartFile file) {
    return cloudinaryService.upload(file);
  }
}
