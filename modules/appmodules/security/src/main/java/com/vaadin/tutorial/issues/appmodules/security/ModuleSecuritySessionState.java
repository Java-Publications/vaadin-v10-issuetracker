package com.vaadin.tutorial.issues.appmodules.security;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;

@VaadinSessionScoped
public class ModuleSecuritySessionState implements Serializable {

  private Long userId;
  private Set<UserRole> roles;


  @Override
  public String toString() {
    return "ModuleSecuritySessionState{" +
           "userId=" + userId +
           ", roles=" + roles +
           '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (! (o instanceof ModuleSecuritySessionState)) return false;
    ModuleSecuritySessionState that = (ModuleSecuritySessionState) o;
    return Objects.equals(userId , that.userId) &&
           Objects.equals(roles , that.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId , roles);
  }

  public Long getUserId() {
    return userId;
  }

  public Set<UserRole> getRoles() {
    return roles;
  }


  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setRoles(Set<UserRole> roles) {
    this.roles = roles;
  }


}
