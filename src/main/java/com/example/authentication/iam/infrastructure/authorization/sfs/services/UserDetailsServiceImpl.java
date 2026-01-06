package com.example.authentication.iam.infrastructure.authorization.sfs.services;

import com.example.authentication.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link UserDetailsService} used by Spring Security
 * to load user-specific data during authentication.
 *
 * <p>This service retrieves users from the persistence layer and maps them
 * to {@link UserDetailsImpl} instances.</p>
 */
@Service(value = "defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @NonNull
  public UserDetails loadUserByUsername(@NonNull String username)
      throws UsernameNotFoundException {

    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User not found with username: " + username));

    return UserDetailsImpl.build(user);
  }
}
