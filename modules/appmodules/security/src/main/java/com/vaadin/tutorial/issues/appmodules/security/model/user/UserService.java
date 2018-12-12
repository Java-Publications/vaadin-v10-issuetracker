package com.vaadin.tutorial.issues.appmodules.security.model.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.tutorial.issues.appmodules.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.persistence.entities.UserRepository;
import com.vaadin.tutorial.issues.webapp.security.password.PasswordEncoder;

public class UserService {

  @Inject private UserRepository userRepository;

  @Inject private UserMapper userMapper;
  @Inject private PasswordEncoder passwordEncoder;


  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(userMapper.fromUser(user));
  }

  public void update(User user , boolean newPassword) {
    if (newPassword) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    userRepository.save(userMapper.fromUser(user));
  }

  public Optional<User> findById(Long id) {
    return userMapper.toUser(userRepository.findById(id));
  }

  public void delete(User user) {
    user.setDeleted(true);
    userRepository.save(userMapper.fromUser(user));
  }

  public List<User> findAll() {
    return userMapper.toUserList(userRepository.findAll());
  }

  public Set<User> find(String value , UserRole role) {
    return userRepository.find(value,)
  }
}
