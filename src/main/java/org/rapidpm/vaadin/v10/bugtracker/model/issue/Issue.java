package org.rapidpm.vaadin.v10.bugtracker.model.issue;

import java.time.LocalDate;
import org.rapidpm.vaadin.v10.bugtracker.model.user.User;

public class Issue {
  private int id;
  private String title;
  private String description;
  private LocalDate date;
  private User owner;
  private User reporter;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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
}
