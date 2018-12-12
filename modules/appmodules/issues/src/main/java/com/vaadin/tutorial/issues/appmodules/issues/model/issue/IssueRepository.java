package com.vaadin.tutorial.issues.appmodules.issues.model.issue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.rapidpm.dependencies.core.logger.HasLogger;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.persistence.JpaConfig;
import com.vaadin.tutorial.issues.persistence.entities.IssueEntity;

@ApplicationScoped
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
                         IssueStatus status ,
                         LocalDate date) {


    logger().info("[IssueRepository] find(..)  ");
    logger().info("[IssueRepository] title " + title);
    logger().info("[IssueRepository] projectId " + projectId);
    logger().info("[IssueRepository] ownerName " + ownerName);
    logger().info("[IssueRepository] reporterName " + reporterName);
    logger().info("[IssueRepository] status " + status);
    logger().info("[IssueRepository] date " + date);


//    CriteriaQuery<Department> c = cb.createQuery(Department.class);
//    Root<Department> dept = c.from(Department.class);
//    Root<Employee> emp = c.from(Employee.class);
//    c.select(dept).distinct(true).where(cb.equal(dept , emp.get("department")));

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<IssueEntity> query = cb.createQuery(IssueEntity.class);
    final Root<IssueEntity> root = query.from(IssueEntity.class);

    final CriteriaQuery<IssueEntity> select = query
        .select(root);
//        .where(cb.parameter(Long.class, ))
//        .where(cb.parameter(Long.class, ))


    final TypedQuery<IssueEntity> q = em.createQuery(select);
//    final List<IssueEntity> resultList = q.getResultList();


    return Set.of();
  }

  //  @Query("select i from Issue i join i.project p join p.members m left join i.owner o where" +
//         " i.reporter.deleted = false" + " and m.id = ?#{principal}" + " and (o is null or o.deleted = false)" +
//         " and i.id = :id" + " and i.reporter.deleted = false")
  public Optional<Issue> findById(Long id) {
    logger().info("[IssueRepository] findById " + id);
    return Optional.empty();
  }

  public void save(Issue issue) {
    logger().info("save issue " + issue);
  }

  public void delete(Issue issue) {
    logger().info("delete issue " + issue);
  }
}
