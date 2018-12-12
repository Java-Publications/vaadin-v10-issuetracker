package com.vaadin.tutorial.issues.appmodules.issues.ui.issues;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.tutorial.issues.appmodules.issues.BugtrackerSessionState;
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.Issue;
import com.vaadin.tutorial.issues.appmodules.issues.model.issue.IssueRepository;
import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.appmodules.issues.model.project.ProjectRepository;
import com.vaadin.tutorial.issues.persistence.entities.UserRepository;

@VaadinSessionScoped
public class IssueService {

  @Inject private IssueRepository issueRepository;
  @Inject private ProjectRepository projectRepository;
  @Inject private UserRepository userRepository;
  @Inject private BugtrackerSessionState bugtrackerSessionState;

  public Set<Issue> find(String title , String ownerName , String reporterName , IssueStatus status , LocalDate date) {
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
    issue.setStatus(IssueStatus.OPEN);
    issue.setDate(LocalDate.now());
    issue.setReporter(userRepository.findById(bugtrackerSessionState.getUserId()).orElse(null));
    issue.setProject(projectRepository.findById(bugtrackerSessionState.getProjectId()).orElse(null));
    update(issue);
  }

}
