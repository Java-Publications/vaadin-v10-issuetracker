package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import org.rapidpm.vaadin.v10.bugtracker.model.user.UserRepository;
import org.rapidpm.vaadin.v10.bugtracker.model.IssueRepository;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.BugtrackerSessionState;
import org.rapidpm.vaadin.v10.bugtracker.model.ProjectRepository;
import com.vaadin.cdi.annotation.VaadinSessionScoped;

@VaadinSessionScoped
public class IssueService {

  @Inject private IssueRepository issueRepository;
  @Inject private ProjectRepository projectRepository;
  @Inject private UserRepository userRepository;
  @Inject private BugtrackerSessionState bugtrackerSessionState;

  public Set<Issue> find(String title , String ownerName , String reporterName , Status status , LocalDate date) {
    return issueRepository.find(bugtrackerSessionState.getProjectId() ,
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
    issue.setReporter(userRepository.findById(bugtrackerSessionState.getUserId()).orElse(null));
    issue.setProject(projectRepository.findById(bugtrackerSessionState.getProjectId()).orElse(null));
    update(issue);
  }

}
