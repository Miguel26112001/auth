package com.example.authentication.iam.domain.model.commands;

import org.springframework.web.multipart.MultipartFile;

/**
 * Command to update a user's profile image.
 *
 * @param userId the ID of the user to update
 * @param file   the multipart file containing the new profile image
 */
public record UpdateUserProfileImageCommand(
    Long userId,
    MultipartFile file
) {}