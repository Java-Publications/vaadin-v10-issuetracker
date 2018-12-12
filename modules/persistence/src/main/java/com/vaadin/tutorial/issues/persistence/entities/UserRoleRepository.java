package com.vaadin.tutorial.issues.persistence.entities;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.tutorial.issues.persistence.JpaConfig;

@ApplicationScoped
public class UserRoleRepository implements HasLogger {

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
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
