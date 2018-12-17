package com.vaadin.tutorial.issues.persistence.entities.module.issues;

import static java.util.stream.Collectors.toList;
import static org.graalvm.compiler.options.OptionType.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;
import com.vaadin.tutorial.issues.persistence.JpaConfig;
import com.vaadin.tutorial.issues.persistence.entities.module.issues.ProjectEntity;

@ApplicationScoped
public class ProjectRepository implements HasLogger {

  @Inject ProjectMapper projectMapper;
  @Inject UserService userService;

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;


  public Set<User> find(Long projectID, String name , UserRole role) {
    return userRepository.find(projectID , name , role);
  }

  public Set<User> findByRole(Long projectID, UserRole role) {
    return userRepository.find(projectID, "" , role);
  }


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
    logger().info("[ProjectRepository] - findAll");

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<ProjectEntity> query = cb.createQuery(ProjectEntity.class);
    final Root<ProjectEntity> root = query.from(ProjectEntity.class);

    final CriteriaQuery<ProjectEntity> select = query
        .select(root);
//        .where();

    final TypedQuery<ProjectEntity> q = em.createQuery(select);
    final List<ProjectEntity> resultList = q.getResultList();

    return resultList
        .stream()
        .map(e -> projectMapper.fromEntity(e))
        .collect(toList());
  }

  public void save(Project project) {
    logger().info("save project " + project);
  }
}
