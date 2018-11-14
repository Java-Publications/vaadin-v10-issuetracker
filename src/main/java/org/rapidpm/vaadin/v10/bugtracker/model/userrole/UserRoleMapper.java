package org.rapidpm.vaadin.v10.bugtracker.model.userrole;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserRoleMapper {

//  UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

  //Mapping from non-enum to enum type
  default UserRole toUserRole(String key) {
    return UserRole.byNameProperty(key);
  }
}
