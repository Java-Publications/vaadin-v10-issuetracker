package org.rapidpm.vaadin.v10.bugtracker.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class UserRepository implements HasLogger {


  public Boolean checkCredentials(String login , String passwd) {
    //TODO impl. needed
    logger().info("checkCredentials for " + login);
    return Boolean.TRUE;
  }

  public User find(long userID) {
    return findById(userID).get();
  }


  //    @Query("select u from Project p join p.members u where" + " p.id = :projectId" +
//            " and lower(u.name) like concat('%', lower(:name), '%')" + " and (:role is null or u.role = :role)")
  public Set<User> find(Long projectId , String name , UserRole role) {
    logger().info("find : " + projectId + " / " + name + " / " + name + " / " + role);
    return Set.of();
  }

  public Optional<User> findById(Long userId) {
    logger().info("findByID id " + userId);
    return Optional.empty();
  }

  public void save(User user) {
    logger().info("save user " + user);
  }

  public List<User> findAll() {
    logger().info("findAll");
    return List.of();
  }
}
