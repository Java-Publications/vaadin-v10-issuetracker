package org.rapidpm.vaadin.v10.bugtracker.persistence.repository;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.meecrowave.jpa.api.Unit;
import org.rapidpm.vaadin.v10.bugtracker.model.SortOrder;
import org.rapidpm.vaadin.v10.bugtracker.model.issue.IssueFilter;
import org.rapidpm.vaadin.v10.bugtracker.model.issue.IssueSortField;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.IssueEntity;
import org.rapidpm.vaadin.v10.bugtracker.webapp.infrastructure.JpaConfig;

public class IssueRepository {
  @Inject
  @Unit(name = JpaConfig.PERSISTENCE_UNIT)
  private EntityManager em;

  public List<IssueEntity> findAll(IssueFilter filter, int offset, int limit,
      IssueSortField sortField, SortOrder sortOrder) {
    return List.of();
  }

  public int count(IssueFilter filter) {
    return 0;
  }

  public Optional<IssueEntity> get(int id) {
    return Optional.empty();
  }

  public void save(IssueEntity entity) {}
}
