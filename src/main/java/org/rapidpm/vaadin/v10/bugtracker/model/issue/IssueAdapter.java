package org.rapidpm.vaadin.v10.bugtracker.model.issue;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.apache.meecrowave.jpa.api.Jpa;
import org.rapidpm.vaadin.v10.bugtracker.model.SortOrder;
import org.rapidpm.vaadin.v10.bugtracker.persistence.entities.IssueEntity;
import org.rapidpm.vaadin.v10.bugtracker.persistence.repository.IssueRepository;


public class IssueAdapter { // implements IssuePort - wenn man es mal mit einer anderen Technologie
                            // ausprobieren möchte
  @Inject
  private IssueMapper issueMapper;
  @Inject
  private IssueRepository repo;

  @Jpa(transactional = true)
  public List<Issue> find(IssueFilter filter, int offset, int limit, IssueSortField sortField,
      SortOrder sortOrder) {
    return issueMapper.map(repo.findAll(filter, offset, limit, sortField, sortOrder));
  }

  public int count(IssueFilter filter) {
    return repo.count(filter);
  }

  @Jpa(transactional = true)
  public void delete(Issue issue) {
    Optional<IssueEntity> entityToDelete = repo.get(issue.getId());
    if (entityToDelete.isPresent()) {
      IssueEntity entity = entityToDelete.get();
      entity.setDeleted(true);
      repo.save(entity);
    } else {
      // throw some exception
    }
  }
}
