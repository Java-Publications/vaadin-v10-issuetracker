package org.rapidpm.vaadin.v10.issuetracker.ui.issues.users;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.domain.Role;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues.Session;
import org.rapidpm.vaadin.v10.issuetracker.ui.security.PasswordEncoder;


public class UserService {

    @Inject private UserRepository userRepository;
    @Inject private Session session;
    @Inject private PasswordEncoder passwordEncoder;

    public Set<User> find(String name, Role role) {
        return userRepository.find(session.getProjectId(), name, role);
    }

    public Set<User> findByRole(Role role) {
        return userRepository.find(session.getProjectId(), "", role);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user, boolean newPassword) {
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
