package com.example.authentication.iam.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserProfileImageCommand(
    Long userId,
    MultipartFile file
) {}