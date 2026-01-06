package com.example.authentication.iam.infrastructure.hashing.bcrypt;

import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Hashing service based on the BCrypt algorithm.
 *
 * <p>This interface combines application-level hashing operations
 * with Spring Security's {@link PasswordEncoder} contract.</p>
 */
public interface BcryptHashingService
    extends HashingService, PasswordEncoder {
}
