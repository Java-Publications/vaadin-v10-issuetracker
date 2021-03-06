package com.vaadin.tutorial.issues.persistence.entities.module.security;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import com.vaadin.tutorial.issues.persistence.PersistenceConfigConstants;

@ApplicationScoped
public class UserRepository implements HasLogger {

  //  @Inject
//  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  @PersistenceContext(unitName= PersistenceConfigConstants.PERSISTENCE_UNIT)
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

//  @Jpa(transactional = true)
  @Transactional()
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

  public Result<UserEntity> findBy(String login , String passwd) {
    return Result.failure("not yet implemented");
  }

  public Set<UserEntity> findByNameAndRole(String username , UserRoleEntity userRole) {
    logger().info("findByNameAndRole : " + username + " / " + userRole);

    return Set.of();
  }
}
