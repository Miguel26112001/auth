package com.example.authentication.iam.domain.model.entities;

import com.example.authentication.iam.domain.exceptions.RoleNotFoundException;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Entity that represents a system role assigned to a user.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20, unique = true)
  private Roles roles;

  /**
   * Creates a new role from a {@link Roles} enum value.
   *
   * @param roles the role enum
   */
  public Role(Roles roles) {
    this.roles = roles;
  }

  /**
   * Returns the role name as a string.
   *
   * @return role name
   */
  public String getStringRole() {
    return this.roles.name();
  }

  /**
   * Returns the default system role.
   *
   * @return default role
   */
  public static Role getDefaultRole() {
    return new Role(Roles.ROLE_USER);
  }

  /**
   * Converts a role name into a {@link Role} instance.
   *
   * @param roleName the role name
   * @return the corresponding role
   * @throws RoleNotFoundException if the role does not exist
   */
  public static Role toRoleFromName(String roleName) {
    try {
      return new Role(Roles.valueOf(roleName));
    } catch (IllegalArgumentException e) {
      throw new RoleNotFoundException(roleName);
    }
  }

  /**
   * Validates a list of roles and assigns a default role if empty or null.
   *
   * @param roles list of roles
   * @return validated role list
   */
  public static List<Role> validateRoleSet(List<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(getDefaultRole());
    }
    return roles;
  }
}
