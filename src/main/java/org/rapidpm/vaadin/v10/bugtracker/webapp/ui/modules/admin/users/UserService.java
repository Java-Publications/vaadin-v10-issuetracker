package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.admin.users;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.model.user.UserRepository;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.webapp.security.password.PasswordEncoder;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.BugtrackerSessionState;

@ApplicationScoped
public class UserService {

  @Inject private UserRepository userRepository;
  @Inject private BugtrackerSessionState bugtrackerSessionState;
  @Inject private PasswordEncoder passwordEncoder;

  public Set<User> find(String name , UserRole role) {
    return userRepository.find(bugtrackerSessionState.getProjectId() , name , role);
  }

  public Set<User> findByRole(UserRole role) {
    return userRepository.find(bugtrackerSessionState.getProjectId() , "" , role);
  }

  public void save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public void update(User user , boolean newPassword) {
    if (newPassword) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    userRepository.save(user);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public void delete(User user) {
    user.setDeleted(true);
    userRepository.save(user);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

}
