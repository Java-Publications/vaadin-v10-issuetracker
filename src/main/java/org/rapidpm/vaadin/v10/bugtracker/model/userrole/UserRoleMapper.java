package org.rapidpm.vaadin.v10.bugtracker.model.userrole;

import java.util.Set;
import org.mapstruct.Mapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserRoleEntity;

@Mapper(componentModel = "cdi")
public interface UserRoleMapper {

  //Mapping from non-enum to enum type
  default UserRole toUserRole(String key) {
    return UserRole.byNameProperty(key);
  }
  

  default UserRole toUserRole(UserRoleEntity e) {
    return UserRole.byNameProperty(e.getName());
  }

  //Mapping Collection element
  Set<UserRole> toRoles(Set<UserRoleEntity> userRoleEntities);
}
