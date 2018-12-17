package com.vaadin.tutorial.issues.webapp.security.model.user;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;


public class User implements Serializable {

  //TODO can be done, but must not
  public static final User ANONYMOUS = new User() {
    {
      setDeleted(false);
      setEmail("xx.xx@xx.xx");
      setName("ANONYMOUS");
      setPassword("");
      setRoles(Set.of());
      setUserId(- 1L);
    }
  };


  @NotNull
  private Long userId;

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  @Email
  private String email;

  @NotNull
  @NotEmpty
  //@Size(min = 6, max = 255)
  //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$", message = "{com.vaadin.tutorial.webapp.invalidPassword}")
  private String password;

//  @Enumerated(EnumType.STRING)

  @NotNull
  @NotEmpty
  private Set<UserRole> roles;

  private boolean deleted;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public Set<UserRole> getRoles() {
    return roles;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }


  @Override
  public String toString() {
    return "User{" +
           "userId=" + userId +
           ", name='" + name + '\'' +
           ", email='" + email + '\'' +
           ", password='" + "xxxx" + '\'' +
           ", roles=" + roles +
           ", deleted=" + deleted +
           '}';
  }
}
