package org.rapidpm.vaadin.v10.issuetracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import org.hibernate.annotations.Where;

@Entity
//@Where(clause = "deleted = false")
public class User extends BusinessAppEntity {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  @Email
  @Column(unique = true)
  private String email;

  @NotNull
  @NotEmpty
  @Size(min = 6, max = 255)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$", message = "{com.example.webapp.invalidPassword}")
  private String password;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Role role;

  private boolean deleted;

  @Override
  public String toString() {
    return "User{" +
           "name='" + name + '\'' +
           ", email='" + email + '\'' +
           ", password='" + password + '\'' +
           ", role=" + role +
           ", deleted=" + deleted +
           '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
