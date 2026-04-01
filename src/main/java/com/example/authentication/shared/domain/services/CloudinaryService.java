package com.example.authentication.shared.domain.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String upload(MultipartFile file);

}
