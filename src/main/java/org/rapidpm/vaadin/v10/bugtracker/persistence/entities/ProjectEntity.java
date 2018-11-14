package org.rapidpm.vaadin.v10.bugtracker.persistence.entities;

import static org.rapidpm.vaadin.v10.bugtracker.persistence.entities.ProjectEntity.TABLE_NAME;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
//@Where(clause = "deleted = false")
@Table(name = TABLE_NAME)
public class ProjectEntity extends CoreEntity {

  public static final String TABLE_NAME = "project";
  public static final String TABLE_NAME_JOIN_USERS = "project_members";

  @NotNull
  @NotEmpty
  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = TABLE_NAME_JOIN_USERS,
      joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = COL_NAME_ID)},
      inverseJoinColumns = {@JoinColumn(name = "members_id", referencedColumnName = COL_NAME_ID)}
  )
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
