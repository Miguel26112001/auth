package com.example.authentication.iam.infrastructure.hashing.bcrypt.service;

import com.example.authentication.iam.infrastructure.hashing.bcrypt.BcryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * BCrypt-based implementation of {@link BcryptHashingService}.
 *
 * <p>This service delegates password hashing and verification
 * to Spring Security's {@link BCryptPasswordEncoder}.</p>
 */
@Service
public class HashingServiceImpl implements BcryptHashingService {

  private final BCryptPasswordEncoder passwordEncoder;

  HashingServiceImpl() {
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Override
  public String encode(CharSequence rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
