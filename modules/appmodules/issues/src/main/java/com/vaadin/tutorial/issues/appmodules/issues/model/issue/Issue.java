package com.vaadin.tutorial.issues.appmodules.issues.model.issue;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vaadin.tutorial.issues.appmodules.issues.model.issuestatus.IssueStatus;
import com.vaadin.tutorial.issues.appmodules.issues.model.projectuser.ProjectUser;
import com.vaadin.tutorial.issues.appmodules.issues.ui.projects.Project;

public class Issue implements Serializable {

  @NotNull
  private Long issueId;

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 255)
  private String title;

  private String description;

  private LocalDate date;

  private ProjectUser owner;

  @NotNull
  private ProjectUser reporter;

  @NotNull
  private IssueStatus status;

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

  public ProjectUser getOwner() {
    return owner;
  }

  public void setOwner(ProjectUser owner) {
    this.owner = owner;
  }

  public ProjectUser getReporter() {
    return reporter;
  }

  public void setReporter(ProjectUser reporter) {
    this.reporter = reporter;
  }

  public IssueStatus getStatus() {
    return status;
  }

  public void setStatus(IssueStatus status) {
    this.status = status;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
