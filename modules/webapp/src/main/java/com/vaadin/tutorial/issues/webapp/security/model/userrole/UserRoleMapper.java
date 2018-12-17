package com.vaadin.tutorial.issues.webapp.security.model.userrole;

import java.util.Collection;
import java.util.Set;

import org.mapstruct.Mapper;
import com.vaadin.tutorial.issues.persistence.entities.module.security.UserRoleEntity;

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


  default UserRoleEntity toEntity(UserRole role){
    throw new RuntimeException("not possible.. find an other way ;-)");
  }


}
