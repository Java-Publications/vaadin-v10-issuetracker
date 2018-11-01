package org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects;

import java.util.List;
import java.util.Optional;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class ProjectRepository implements HasLogger {

  public List<Project> findByMembersIn(User user) {
    logger().info("findByMembersIn user " + user);
    return List.of();
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
