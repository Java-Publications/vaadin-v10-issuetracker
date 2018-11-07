package org.rapidpm.vaadin.v10.bugtracker.persistence.entities;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class IssueEntity extends CoreEntity {

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 255)
  private String title;

  private String description;

  private LocalDate date;

  @ManyToOne
//  @Where(clause = "deleted = false")
  private UserEntity owner;

  @ManyToOne
  @NotNull
//  @Where(clause = "deleted = false")
  private UserEntity reporter;

  @Enumerated(EnumType.STRING)
  @NotNull
  private StatusEntity status;

  @ManyToOne
  @NotNull
//  @Where(clause = "deleted = false")
  private ProjectEntity project;


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

  public UserEntity getOwner() {
    return owner;
  }

  public void setOwner(UserEntity owner) {
    this.owner = owner;
  }

  public UserEntity getReporter() {
    return reporter;
  }

  public void setReporter(UserEntity reporter) {
    this.reporter = reporter;
  }

  public StatusEntity getStatus() {
    return status;
  }

  public void setStatus(StatusEntity status) {
    this.status = status;
  }

  public ProjectEntity getProject() {
    return project;
  }

  public void setProject(ProjectEntity project) {
    this.project = project;
  }
}
