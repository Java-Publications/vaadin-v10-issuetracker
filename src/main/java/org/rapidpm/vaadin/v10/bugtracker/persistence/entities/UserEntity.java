package org.rapidpm.vaadin.v10.bugtracker.persistence.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = UserEntity.TABLE_NAME)
public class UserEntity extends CoreEntity {

  public static final String TABLE_NAME = "login";
  public static final String TABLE_NAME_JOIN_USER_ROLES = "login_userrole";

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
  //TODO remove this here
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$", message = "{com.example.webapp.invalidPassword}")
  private String password;

  //@Enumerated(EnumType.STRING)
//  @Transient
  @OneToMany
  @NotNull
  @JoinTable(
      name = TABLE_NAME_JOIN_USER_ROLES,
      joinColumns = {@JoinColumn(name = "login_id", referencedColumnName = COL_NAME_ID)},
      inverseJoinColumns = {@JoinColumn(name = "userrole_id", referencedColumnName = COL_NAME_ID, unique = true)}
  )
  private Set<UserRoleEntity> roles;


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

  public Set<UserRoleEntity> getRoles() {
    return roles;
  }

  public void setRole(Set<UserRoleEntity> roles) {
    this.roles = roles;
  }


}
