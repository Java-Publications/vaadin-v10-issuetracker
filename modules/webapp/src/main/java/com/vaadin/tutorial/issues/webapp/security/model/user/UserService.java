package com.vaadin.tutorial.issues.webapp.security.model.user;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.rapidpm.frp.model.Result;
import com.vaadin.tutorial.issues.persistence.entities.module.security.UserRepository;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRole;
import com.vaadin.tutorial.issues.webapp.security.model.userrole.UserRoleMapper;
import com.vaadin.tutorial.issues.webapp.security.password.PasswordEncoder;

public class UserService {

  @Inject private UserRepository userRepository;

  @Inject private UserMapper userMapper;
  @Inject private UserRoleMapper userRoleMapper;
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
    return userRepository.findById(id)
                         .stream()
                         .map(ue -> userMapper.toUser(ue))
                         .findFirst();
  }

  public void delete(User user) {
    user.setDeleted(true);
    userRepository.save(userMapper.fromUser(user));
  }

  public List<User> findAll() {
    return userMapper.toUserList(userRepository.findAll());
  }

  public List<User> find(String username , UserRole role) {
    return userMapper.toUserList(userRepository.findByNameAndRole(username , userRoleMapper.toEntity(role)));
  }

  public List<User> findByIds(Long... userids) {
    return List.of();
  }

  public Boolean checkCredentials(String login , String passwd) {
    return userRepository.checkCredentials(login , passwd);
  }

  public Result<User> loadFor(String login , String passwd) {
    return userRepository
        .findBy(login , passwd)
        .map(e -> userMapper.toUser(e));
  }
}
