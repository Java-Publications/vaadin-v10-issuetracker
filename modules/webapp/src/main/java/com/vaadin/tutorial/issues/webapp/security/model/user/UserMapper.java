package com.vaadin.tutorial.issues.webapp.security.model.user;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vaadin.tutorial.issues.persistence.entities.module.security.UserEntity;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRoleMapper;

@Mapper(
    uses = {UserRoleMapper.class},
    componentModel = "cdi")
public interface UserMapper {

  //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  //TODO manual impl, mapstruct is used later
  //TODO make a hint inside the article -> mapstruct
  @Mapping(source = "id", target = "userId")
  User toUser(UserEntity e);

  @Mapping(source = "userId", target = "id")
  UserEntity fromUser(User e);


  //  @Mapping(source = "id", target = "userId")
  List<User> toUserList(Collection<UserEntity> e);


}
