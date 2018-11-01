package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues;

import java.io.Serializable;
import java.util.Objects;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class Session implements Serializable {

    private Long projectId;
    private Long userId;
    private Role role;


    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(projectId , session.projectId) &&
               Objects.equals(userId , session.userId) &&
               role == session.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId , userId , role);
    }

    @Override
    public String toString() {
        return "Session{" +
               "projectId=" + projectId +
               ", userId=" + userId +
               ", role=" + role +
               '}';
    }

    public void setUserId(Long userId) {

    }

    public void setRole(Role role) {

    }

    public void setProjectId(Long id) {

    }
}
