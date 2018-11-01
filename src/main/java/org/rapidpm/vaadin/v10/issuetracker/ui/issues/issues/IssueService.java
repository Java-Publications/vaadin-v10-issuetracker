package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.ProjectRepository;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.users.UserRepository;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class IssueService {

  @Inject private IssueRepository issueRepository;
  @Inject private ProjectRepository projectRepository;
  @Inject private UserRepository userRepository;
  @Inject private Session session;

  public Set<Issue> find(String title , String ownerName , String reporterName , Status status , LocalDate date) {
    return issueRepository.find(session.getProjectId() ,
                                title ,
                                ownerName.isEmpty() ? null : ownerName ,
                                reporterName ,
                                status ,
                                date);
  }

  public Optional<Issue> findById(Long issueId) {
    return issueRepository.findById(issueId);
  }

  public void update(Issue issue) {
    issueRepository.save(issue);
  }

  public void delete(Issue issue) {
    issueRepository.delete(issue);
  }

  public void create(Issue issue) {
    issue.setStatus(Status.OPEN);
    issue.setDate(LocalDate.now());
    issue.setReporter(userRepository.findById(session.getUserId()).orElse(null));
    issue.setProject(projectRepository.findById(session.getProjectId()).orElse(null));
    update(issue);
  }

}
