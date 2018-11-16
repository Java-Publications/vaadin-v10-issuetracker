package org.rapidpm.vaadin.v10.bugtracker.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.ProjectEntity;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.Project;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class ProjectRepository implements HasLogger {

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;


  public List<Project> findByMembersIn(User user) {
    logger().info("findByMembersIn user " + user);

    TypedQuery<ProjectEntity> query = em
        .createQuery("select p from ProjectEntity as p where p.deleted=false" ,
                     ProjectEntity.class);
    return query
        .getResultList()
        .stream()
        .map(e -> {
          Project prj = new Project();
          prj.setDeleted(e.isDeleted());

          prj.setMembers(e
                             .getMembers()
                             .stream()
                             .map(m -> {
                               return new User();
//            member.set
                             })
                             .collect(Collectors.toSet())
          );
          prj.setName(e.getName());
          prj.setProjectId(e.getId());
          return prj;
        })
    .collect(toList());
  }

  public Optional<Project> findById(Long projectId) {
    logger().info("findById projectID " + projectId);
    return Optional.empty();
  }

  public List<Project> findAll() {
    logger().info("findAll");
    return List.of();
  }

  public void save(Project project) {
    logger().info("save project " + project);
  }
}
