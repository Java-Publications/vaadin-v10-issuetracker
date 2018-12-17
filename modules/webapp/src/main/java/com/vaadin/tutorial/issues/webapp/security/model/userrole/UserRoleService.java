package com.vaadin.tutorial.issues.webapp.security.model.userrole;

import java.util.Set;

import javax.inject.Inject;

import com.vaadin.tutorial.issues.persistence.entities.module.security.UserRoleRepository;

public class UserRoleService {

  @Inject private UserRoleRepository userRoleRepository;
  @Inject private UserRoleMapper userRoleMapper;


  public Set<UserRole> findAll() {
    return userRoleMapper.toRoles(userRoleRepository.findAll());
  }
}
