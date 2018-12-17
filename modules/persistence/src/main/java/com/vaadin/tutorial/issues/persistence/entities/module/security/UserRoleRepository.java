package com.vaadin.tutorial.issues.persistence.entities.module.security;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.tutorial.issues.persistence.PersistenceConfigConstants;

@ApplicationScoped
public class UserRoleRepository implements HasLogger {

  @Inject
//  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  @PersistenceContext(unitName = PersistenceConfigConstants.PERSISTENCE_UNIT)
  private EntityManager em;

  public List<UserRoleEntity> findAll() {
    logger().info("findAll..");
    return em.createQuery("select r from UserRoleEntity r where r.deleted = false" ,
                          UserRoleEntity.class)
             .getResultList()
             .stream()
             .collect(toList());
  }


}
