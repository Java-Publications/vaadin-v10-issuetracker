package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;

public class Project {

    @NotNull
    private Long projectId;

    @NotNull
    @NotEmpty
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> members;

    private boolean deleted;


    @Override
    public String toString() {
        return "Project{" +
               "name='" + name + '\'' +
               ", members=" + members +
               ", deleted=" + deleted +
               '}';
    }


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
