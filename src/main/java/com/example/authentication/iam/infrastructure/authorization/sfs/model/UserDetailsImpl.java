package com.example.authentication.iam.infrastructure.authorization.sfs.model;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom {@link UserDetails} implementation used by Spring Security.
 * Wraps the domain {@link User} aggregate.
 */
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

  private final Long id;
  private final String username;

  @JsonIgnore
  private final String password;

  private final boolean accountNonExpired;
  private final boolean accountNonLocked;
  private final boolean credentialsNonExpired;
  private final boolean enabled;
  private final Collection<? extends GrantedAuthority> authorities;

  /**
   * Creates a new {@code UserDetailsImpl}.
   *
   * @param id user identifier
   * @param username username
   * @param password encoded password
   * @param enabled whether the user is enabled
   * @param authorities granted authorities
   */
  public UserDetailsImpl(
      Long id,
      String username,
      String password,
      boolean enabled,
      Collection<? extends GrantedAuthority> authorities) {

    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = enabled;
  }

  /**
   * Builds a {@code UserDetailsImpl} instance from a domain {@link User}.
   *
   * @param user domain user
   * @return user details instance
   */
  public static UserDetailsImpl build(User user) {
    var authorities =
        user.getRoles().stream()
            .map(role -> role.getRoles().name())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getUsername(),
        user.getHashedPassword(),
        user.isActive(),
        authorities);
  }
}
