package org.rapidpm.vaadin.v10.bugtracker.model.user;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRoleMapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserRoleEntity;

@Mapper(uses = {
    UserRoleMapper.class
})
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //@Mapping(source = "email", target = "seatCount")
  User toUser(UserEntity e);


  //Mapping Collection element
  Set<UserRole> toRoles(Set<UserRoleEntity> e);

  UserRole toRole(UserRoleEntity e);

}
