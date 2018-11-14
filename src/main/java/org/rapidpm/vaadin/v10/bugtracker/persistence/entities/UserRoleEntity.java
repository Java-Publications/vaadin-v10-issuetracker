package org.rapidpm.vaadin.v10.bugtracker.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = UserRoleEntity.TABLE_NAME)
public class UserRoleEntity extends CoreEntity {

  public static final String TABLE_NAME = "userrole";


  @NotNull
//  @UniqueConstraint(name = "")
  private String name;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return "UserRoleEntity{" +
           "name='" + name + '\'' +
           '}';
  }
}
