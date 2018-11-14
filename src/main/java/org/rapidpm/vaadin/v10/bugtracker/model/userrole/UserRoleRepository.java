package org.rapidpm.vaadin.v10.bugtracker.model.userrole;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.model.userrole.UserRole;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.UserRoleEntity;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;

@ApplicationScoped
public class UserRoleRepository implements HasLogger {

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;

  public List<UserRole> findAll() {
    logger().info("findAll..");
    return em.createQuery("select r from UserRoleEntity r where r.deleted = false" ,
                          UserRoleEntity.class)
             .getResultList()
             .stream()
             .map(e -> UserRole.byNameProperty(e.getName()))
             .collect(toList());
  }


}
