package org.rapidpm.vaadin.v10.bugtracker.persistence;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.rapidpm.vaadin.v10.bugtracker.model.User;

@Entity
//@Where(clause = "deleted = false")
public class ProjectEntity extends CoreEntity {

    @NotNull
    @NotEmpty
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserEntity> members;

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

    public Set<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
