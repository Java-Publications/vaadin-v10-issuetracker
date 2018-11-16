package org.rapidpm.vaadin.v10.bugtracker.model.user;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.meecrowave.jpa.api.Jpa;
import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;

@ApplicationScoped
public class UserRepository implements HasLogger {

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;

  @Inject private UserMapper userMapper;


  public Boolean checkCredentials(String login , String passwd) {
    //TODO impl. needed
    logger().info("checkCredentials for " + login);
    return Boolean.TRUE;
  }

  //    @Query("select u from Project p join p.members u where" + " p.id = :projectId" +
//            " and lower(u.name) like concat('%', lower(:name), '%')" + " and (:role is null or u.role = :role)")
  public Set<User> find(Long projectId , String name , UserRole role) {
    logger().info("find : " + projectId + " / " + name + " / " + name + " / " + role);
    throw new RuntimeException("not yet implemented : "
                               + UserRepository.class.getSimpleName()
                               + " - find(Long projectId , String name , UserRole role)");
//    return Set.of();
  }

  @Jpa(transactional = true)
  public Optional<User> findById(Long userId) {
    logger().info("findByID id " + userId);
    UserEntity userEntity = em.find(UserEntity.class , userId);
    if (userEntity != null) {
//      User user = new User();
//      user.setUserId(userEntity.getId());
//      user.setName(userEntity.getName());
//      user.setEmail(userEntity.getEmail());
//      Set<UserRoleEntity> roles = userEntity.getRoles();
//
//      user.setRoles(
//          roles
//              .stream()
//              .map(e -> byNameProperty(e.getName()))
//              .peek(r -> logger().info("findById add role to user " + r))
//              .collect(toSet())
//      );
//      return Optional.of(user);

      return Optional.ofNullable(userMapper.toUser(userEntity));
    } else {
      return Optional.empty();
    }

  }

  public void save(User user) {
    logger().info("save user " + user);
  }

  public List<User> findAll() {
    logger().info("findAll");
    return em
        .createQuery("select u from UserEntity u where u.deleted=false" , UserEntity.class)
        .getResultList()
        .stream()
        .map(e -> userMapper.toUser(e))
        .collect(Collectors.toList());
  }
}
