package org.rapidpm.vaadin.v10.bugtracker.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.apache.meecrowave.jpa.api.Jpa;
import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserEntity;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;

@ApplicationScoped
public class UserRepository implements HasLogger {
@Inject
private DataSource ds;
  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;
  
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
@Jpa(transactional=true)
  public Optional<User> findById(Long userId) {
    logger().info(ds.toString());
    logger().info("findByID id " + userId);
    UserEntity userEntity = em.find(UserEntity.class, userId);
    if(userEntity!=null) {
      User user=new User();
      user.setUserId(userEntity.getId());
      user.setName(userEntity.getName());
      return Optional.ofNullable(user);      
    }
    else {
      return Optional.empty();
    }
    
  }

  public void save(User user) {
    logger().info("save user " + user);
  }

  public List<User> findAll() {
    logger().info("findAll");
    return List.of();
  }
}
