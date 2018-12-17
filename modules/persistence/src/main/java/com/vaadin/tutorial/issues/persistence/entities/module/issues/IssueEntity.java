package com.vaadin.tutorial.issues.persistence.entities.module.issues;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vaadin.tutorial.issues.persistence.entities.CoreEntity;
import com.vaadin.tutorial.issues.persistence.entities.module.security.UserEntity;


@Entity
@Table(name = IssueEntity.TABLE_NAME)
public class IssueEntity extends CoreEntity {

  public static final String TABLE_NAME = "issue";

  @NotNull
  @NotEmpty
  @Size(min = 3, max = 255)
  private String title;

  @Lob
  @Column
  private String description;

  private LocalDate date;

  @OneToOne
  @Column(name = "owner_id")
  private UserEntity owner;

  @OneToOne
  @NotNull
  @Column(name = "reporter_id")
  private UserEntity reporter;

  @OneToOne
  @NotNull
  @Column(name = "status_id")
  private IssueStatusEntity status;

  @OneToOne
  @NotNull
  @Column(name = "project_id")
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

  public IssueStatusEntity getStatus() {
    return status;
  }

  public void setStatus(IssueStatusEntity status) {
    this.status = status;
  }

  public ProjectEntity getProject() {
    return project;
  }

  public void setProject(ProjectEntity project) {
    this.project = project;
  }
}
