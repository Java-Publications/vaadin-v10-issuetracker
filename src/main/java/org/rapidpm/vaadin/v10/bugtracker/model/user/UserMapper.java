package org.rapidpm.vaadin.v10.bugtracker.model.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRoleMapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;

@Mapper(
    uses = {UserRoleMapper.class},
    componentModel = "cdi")
public interface UserMapper {

  //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //TODO manual impl, mapstruct is used later
  //TODO make a hint inside the article -> mapstruct
  @Mapping(source = "id", target = "userId")
  User toUser(UserEntity e);


}
