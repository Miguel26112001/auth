package com.example.authentication.iam.domain.model.aggregates;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * User aggregate root representing an application user.
 * Handles identity, credentials, status, and assigned roles.
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

  @NotBlank
  @Size(max = 50)
  @Column(unique = true)
  private String username;

  @Email
  @NotBlank
  @Size(max = 50)
  @Column(unique = true)
  private String email;

  @NotBlank
  @Size(max = 100)
  private String hashedPassword;

  @Column(nullable = false)
  private boolean isActive;

  @Column(nullable = false)
  private boolean isVerified;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {
          CascadeType.DETACH,
          CascadeType.MERGE,
          CascadeType.PERSIST,
          CascadeType.REFRESH
      })
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  /**
   * Default constructor.
   * Initializes the user with active status and no roles.
   */
  public User() {
    this.roles = new HashSet<>();
    this.isActive = true;
    this.isVerified = false;
  }

  /**
   * Creates a user with basic credentials.
   *
   * @param username user's username
   * @param email user's email
   * @param hashedPassword hashed password
   */
  public User(String username, String email, String hashedPassword) {
    this.username = username;
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.roles = new HashSet<>();
    this.isActive = true;
    this.isVerified = false;
  }

  /**
   * Creates a user with predefined roles.
   *
   * @param username user's username
   * @param email user's email
   * @param hashedPassword hashed password
   * @param roles initial roles
   */
  public User(
      String username,
      String email,
      String hashedPassword,
      List<Role> roles) {
    this(username, email, hashedPassword);
    addRoles(roles);
  }

  /**
   * Adds validated roles to the user.
   *
   * @param roles roles to add
   */
  public void addRoles(List<Role> roles) {
    var validatedRoleSet = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoleSet);
  }
}
