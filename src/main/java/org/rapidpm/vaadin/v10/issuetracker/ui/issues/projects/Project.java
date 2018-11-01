package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.rapidpm.vaadin.v10.issuetracker.domain.BusinessAppEntity;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;

@Entity
//@Where(clause = "deleted = false")
public class Project extends BusinessAppEntity {

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
