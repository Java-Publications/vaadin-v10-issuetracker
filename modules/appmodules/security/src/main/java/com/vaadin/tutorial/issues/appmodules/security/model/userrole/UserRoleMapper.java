package com.vaadin.tutorial.issues.appmodules.security.model.userrole;

import java.util.Collection;
import java.util.Set;

import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.persistence.entities.UserRoleEntity;

@Mapper(componentModel = "cdi")
public interface UserRoleMapper {

//  UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

  //Mapping from non-enum to enum type
  default UserRole toUserRole(String key) {
    return UserRole.byNameProperty(key);
  }

  default UserRole toUserRole(UserRoleEntity e) {
    return UserRole.byNameProperty(e.getName());
  }

  //Mapping Collection element
  Set<UserRole> toRoles(Collection<UserRoleEntity> userRoleEntities);


}