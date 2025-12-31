package com.example.authentication.iam.domain.model.aggregates;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  public User() {
    this.roles = new HashSet<>();
    this.isActive = true;
    this.isVerified = false;
  }

  public User(String username, String email, String hashedPassword) {
    this.username = username;
    this.email = email;
    this.hashedPassword = hashedPassword;
    this.roles = new HashSet<>();
    this.isActive = true;
    this.isVerified = false;
  }

  public User(String username, String email, String hashedPassword, List<Role> roles) {
    this(username, email, hashedPassword);
    addRoles(roles);
  }

  public void addRoles(List<Role> roles) {
    var validatedRoleSet = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoleSet);
  }
}
