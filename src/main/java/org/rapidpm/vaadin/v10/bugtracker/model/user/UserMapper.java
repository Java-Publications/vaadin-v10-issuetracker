package org.rapidpm.vaadin.v10.bugtracker.model.user;

import java.util.Set;

import org.mapstruct.Mapper;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRoleMapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserRoleEntity;

@Mapper(
    uses = {UserRoleMapper.class},
    componentModel = "cdi")
public interface UserMapper {

  //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //@Mapping(source = "email", target = "seatCount")
  User toUser(UserEntity e);

  default UserRole toUserRole(UserRoleEntity e) {
    return UserRole.byNameProperty(e.getName());
  }

  //Mapping Collection element
  Set<UserRole> toRoles(Set<UserRoleEntity> userRoleEntities);


}
