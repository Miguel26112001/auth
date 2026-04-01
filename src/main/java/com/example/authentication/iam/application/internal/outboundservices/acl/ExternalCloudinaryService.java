package com.example.authentication.iam.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.authentication.shared.interfaces.acl.CloudinaryServiceContextFacade;

@Service
public class ExternalCloudinaryService {

  private final CloudinaryServiceContextFacade cloudinaryServiceContextFacade;

  public ExternalCloudinaryService(CloudinaryServiceContextFacade cloudinaryServiceContextFacade) {
    this.cloudinaryServiceContextFacade = cloudinaryServiceContextFacade;
  }

  public String uploadImage(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      return "";
    }

    try {
      return cloudinaryServiceContextFacade.uploadFile(file);
    } catch (Exception e) {
      // Logueamos el error, pero mantenemos la resiliencia del servicio de IAM
      System.err.println("Error al subir imagen a través de la fachada ACL: " + e.getMessage());
      return "";
    }
  }
}
