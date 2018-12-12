package com.vaadin.tutorial.issues.persistence.entities;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.tutorial.issues.persistence.JpaConfig;

//@ApplicationScoped
public class UserRepository implements HasLogger {

  //  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;


  public Boolean checkCredentials(String login , String passwd) {
    //TODO impl. needed
    logger().info("checkCredentials for " + login);
    return Boolean.TRUE;
  }

  //    @Query("select u from Project p join p.members u where" + " p.id = :projectId" +
//            " and lower(u.name) like concat('%', lower(:name), '%')" + " and (:role is null or u.role = :role)")
  public Set<UserEntity> find(Long projectId , String name , UserRoleEntity role) {
    logger().info("find : " + projectId + " / " + name + " / " + name + " / " + role);

    return Set.of();
  }

  @Jpa(transactional = true)
  public Optional<UserEntity> findById(Long userId) {
    logger().info("findByID id " + userId);
    UserEntity userEntity = em.find(UserEntity.class , userId);
    return ofNullable(userEntity);
  }

  public void save(UserEntity user) {
    logger().info("save user " + user);
  }

  public List<UserEntity> findAll() {
    logger().info("findAll");
    return em
        .createQuery("select u from UserEntity u where u.deleted=false" , UserEntity.class)
        .getResultList()
        .stream()
//        .map(e -> userMapper.toUser(e))
        .collect(toList());
  }
}
