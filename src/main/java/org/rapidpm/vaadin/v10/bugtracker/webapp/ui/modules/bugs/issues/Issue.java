package org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.issues;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.rapidpm.vaadin.v10.bugtracker.model.user.User;
import org.rapidpm.vaadin.v10.bugtracker.webapp.ui.modules.bugs.projects.Project;

public class Issue {

  @NotNull
  private Long issueId;

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 255)
  private String title;

  private String description;

  private LocalDate date;

  private User owner;

  @NotNull
  private User reporter;

  @NotNull
  private Status status;

  @NotNull
  private Project project;


  public String toString() {
    return "Issue{" +
           "title='" + title + '\'' +
           ", description='" + description + '\'' +
           ", date=" + date +
           ", owner=" + owner +
           ", reporter=" + reporter +
           ", status=" + status +
           ", project=" + project +
           '}';
  }

  public Long getIssueId() {
    return issueId;
  }

  public void setIssueId(Long issueId) {
    this.issueId = issueId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public User getReporter() {
    return reporter;
  }

  public void setReporter(User reporter) {
    this.reporter = reporter;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
