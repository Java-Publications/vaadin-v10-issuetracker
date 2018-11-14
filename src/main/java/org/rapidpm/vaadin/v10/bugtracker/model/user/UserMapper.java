package org.rapidpm.vaadin.v10.bugtracker.model.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRoleMapper;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;

@Mapper(uses = {UserRoleMapper.class}, componentModel = "cdi")
public interface UserMapper {
  @Mapping(target = "userId", source = "id")
  User toUser(UserEntity userEntity);
}
