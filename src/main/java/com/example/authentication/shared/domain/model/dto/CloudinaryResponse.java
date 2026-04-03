package com.example.authentication.shared.domain.model.dto;

/**
 * Data Transfer Object representing a response from Cloudinary.
 *
 * @param url      the secure URL of the uploaded media
 * @param publicId the unique identifier used by Cloudinary for management and deletion
 */
public record CloudinaryResponse(String url, String publicId) {}