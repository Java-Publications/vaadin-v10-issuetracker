package org.rapidpm.vaadin.v10.issuetracker.ui.issues.issues;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.rapidpm.vaadin.v10.issuetracker.domain.BusinessAppEntity;
import org.rapidpm.vaadin.v10.issuetracker.domain.User;
import org.rapidpm.vaadin.v10.issuetracker.ui.issues.projects.Project;

public class Issue extends BusinessAppEntity {

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 255)
  private String title;

  private String description;

  private LocalDate date;

  @ManyToOne
//  @Where(clause = "deleted = false")
  private User owner;

  @ManyToOne
  @NotNull
//  @Where(clause = "deleted = false")
  private User reporter;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Status status;

  @ManyToOne
  @NotNull
//  @Where(clause = "deleted = false")
  private Project project;


  @Override
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
