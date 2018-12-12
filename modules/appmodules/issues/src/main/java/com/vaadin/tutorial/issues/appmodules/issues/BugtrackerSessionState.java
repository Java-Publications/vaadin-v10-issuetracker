package com.vaadin.tutorial.issues.appmodules.issues;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class BugtrackerSessionState implements Serializable {

    private Long projectId;
    private Long userId;
    private Set<UserRole> roles;


    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof BugtrackerSessionState)) return false;
        BugtrackerSessionState that = (BugtrackerSessionState) o;
        return Objects.equals(projectId , that.projectId) &&
               Objects.equals(userId , that.userId) &&
               Objects.equals(roles , that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId , userId , roles);
    }

    @Override
    public String toString() {
        return "BugtrackerSessionState{" +
               "projectId=" + projectId +
               ", userId=" + userId +
               ", roles=" + roles +
               '}';
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }


}
