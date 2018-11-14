package org.rapidpm.vaadin.v10.bugtracker.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Issue;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues.Status;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class IssueRepository implements HasLogger {

  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;


  //  @Query("select i from Issue i join i.project p left join i.owner o where" + " p.id = :projectId" +
//         " and lower(i.title) like concat('%', lower(:title), '%')" +
//         " and (:owner is null or lower(o.name) like concat('%', lower(:owner), '%'))" +
//         " and lower(i.reporter.name) like concat('%', lower(:reporter), '%')" +
//         " and (:status is null or i.status = :status)" + " and (:date is null or i.date = :date)" +
//         " and i.reporter.deleted = false" + " and (o is null or o.deleted = false)")
  public Set<Issue> find(Long projectId ,
                         String title ,
                         String ownerName ,
                         String reporterName ,
                         Status status ,
                         LocalDate date) {


    logger().info("find  " );
    return Set.of();
  }

  //  @Query("select i from Issue i join i.project p join p.members m left join i.owner o where" +
//         " i.reporter.deleted = false" + " and m.id = ?#{principal}" + " and (o is null or o.deleted = false)" +
//         " and i.id = :id" + " and i.reporter.deleted = false")
  public Optional<Issue> findById(Long id) {
    logger().info("findById " + id);
    return Optional.empty();
  }

  public void save(Issue issue) {
    logger().info("save issue " + issue);
  }

  public void delete(Issue issue) {
    logger().info("delete issue " + issue);
  }
}
